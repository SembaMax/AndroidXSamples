package com.semba.androidsamples.ArchPagination.Data

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.semba.androidsamples.ArchPagination.Data.Models.NewsModel
import com.semba.androidsamples.Shared.Constants
import io.reactivex.disposables.CompositeDisposable
import org.json.JSONObject

class NewsDataSourceClass(private val repository: Repository, private val compositeDisposable: CompositeDisposable): PageKeyedDataSource<Int, NewsModel>() {

    private var gson: Gson
    private var sourceIndex: Int = 1
    private var progressLiveStatus: MutableLiveData<String>

    init {
        this.progressLiveStatus = MutableLiveData()
        val builder = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        this.gson = builder.setLenient().create()
    }

    fun getProgressLiveData(): MutableLiveData<String> = this.progressLiveStatus

    @SuppressLint("CheckResult")
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, NewsModel>) {
        repository.executeNewsApi(sourceIndex)
            .doOnSubscribe {
                compositeDisposable.add(it)
                progressLiveStatus.postValue(Constants.LOADING)
            }.subscribe {
                progressLiveStatus.postValue(Constants.LOADED)
                val obj = JSONObject(gson.toJson(it))
                val array = obj.getJSONArray("articles")
                val articlesList = ArrayList<NewsModel>()

                for (i in 0..array.length())
                {
                    articlesList.add(NewsModel(array.getJSONObject(i).optString("title"), array.getJSONObject(i).optString("urlToImage")))
                }
                sourceIndex++
                callback.onResult(articlesList,null,sourceIndex)
            }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, NewsModel>) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, NewsModel>) {
        repository.executeNewsApi(params.key)
            .doOnSubscribe {
                compositeDisposable.add(it)
                progressLiveStatus.postValue(Constants.LOADING)
            }
            .subscribe {
                progressLiveStatus.postValue(Constants.LOADED)
                val obj = JSONObject(gson.toJson(it))
                val array = obj.getJSONArray("articles")
                val articlesList = ArrayList<NewsModel>()

                for (i in 0..array.length())
                {
                    articlesList.add(NewsModel(array.getJSONObject(i).optString("title"), array.getJSONObject(i).optString("urlToImage")))
                }
                sourceIndex++
                callback.onResult(articlesList,if (params.key == 3) null else params.key + 1)
            }
    }
}