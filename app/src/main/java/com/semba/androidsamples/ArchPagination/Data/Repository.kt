package com.semba.androidsamples.ArchPagination.Data

import com.google.gson.JsonElement
import com.semba.androidsamples.ArchPagination.API.ApiCallInterface
import io.reactivex.Observable

class Repository(private val apiCallInterface: ApiCallInterface) {

    fun executeNewsApi(index: Int): Observable<JsonElement>
    {
        return apiCallInterface.fetchNewsList(Constants.sources[index], Constants.API_KEY)
    }
}