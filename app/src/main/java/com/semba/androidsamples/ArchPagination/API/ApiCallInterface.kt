package com.semba.androidsamples.ArchPagination.API

import com.google.gson.JsonElement
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCallInterface {

    @GET(URLs.NewsItems)
    fun fetchNewsList(@Query("source") source:String, @Query("apiKey") apiKey: String) : Observable<JsonElement>
}