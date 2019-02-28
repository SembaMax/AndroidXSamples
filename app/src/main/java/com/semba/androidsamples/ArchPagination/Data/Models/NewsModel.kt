package com.semba.androidsamples.ArchPagination.Data.Models

import com.google.gson.annotations.SerializedName

data class NewsModel(@SerializedName("") private val title: String,
                     @SerializedName("") private val urlToImage: String)