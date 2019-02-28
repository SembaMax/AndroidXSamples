package com.semba.androidsamples.ArchPagination.MainScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.semba.androidsamples.ArchPagination.Data.Models.NewsModel
import com.semba.androidsamples.ArchPagination.Data.NewsDataSourceClass
import com.semba.androidsamples.ArchPagination.Data.NewsDataSourceFactory
import com.semba.androidsamples.ArchPagination.Data.Repository
import io.reactivex.disposables.CompositeDisposable

class NewsScreenViewModel: ViewModel {

    private var newsDataSourceFactory: NewsDataSourceFactory?
    private var liveData: LiveData<PagedList<NewsModel>>? = null
    private var progressLoadStatus: LiveData<String>? = null
    private var compositeDisposable = CompositeDisposable()

    constructor(repository: Repository) : super()
    {
        newsDataSourceFactory = NewsDataSourceFactory(repository,compositeDisposable)
        initPaging()
    }

    private fun initPaging() {
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(10)
            .setPageSize(10)
            .build()

        liveData = LivePagedListBuilder(newsDataSourceFactory!!,pagedListConfig).build()
        progressLoadStatus = Transformations.switchMap(newsDataSourceFactory!!.liveData, NewsDataSourceClass::getProgressLiveData)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}