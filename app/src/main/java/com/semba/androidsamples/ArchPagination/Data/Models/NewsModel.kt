package com.semba.androidsamples.ArchPagination.Data.Models

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

data class NewsModel(@SerializedName("") val title: String,
                     @SerializedName("") val urlToImage: String) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NewsModel>(){
            override fun areItemsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
                return oldItem.title.equals(newItem.title)
            }

            override fun areContentsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
                return oldItem.equals(newItem)
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == this)
            return true
        val obj = other as NewsModel
        return obj.title.equals(this.title)
    }
}