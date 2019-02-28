package com.semba.androidsamples.ArchPagination.Data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.semba.androidsamples.ArchPagination.Data.Models.NewsModel
import io.reactivex.disposables.CompositeDisposable

class NewsDataSourceFactory: DataSource.Factory<Int, NewsModel> {

    private var repository: Repository
    var liveData: MutableLiveData<NewsDataSourceClass>
    private var compositeDisposable: CompositeDisposable

    constructor(repository: Repository, compositeDisposable: CompositeDisposable) : super() {
        this.repository = repository
        this.compositeDisposable = compositeDisposable
        this.liveData = MutableLiveData()
    }

    override fun create(): DataSource<Int, NewsModel> {
        val newsDataSourceClass = NewsDataSourceClass(this.repository,this.compositeDisposable)
        liveData.postValue(newsDataSourceClass)
        return newsDataSourceClass
    }
}