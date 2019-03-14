package com.semba.androidsamples.ArchPagination.MainScreen.Adapters

import androidx.paging.PagedListAdapter
import com.semba.androidsamples.ArchPagination.Data.Models.NewsModel

class NewsAdapter: PagedListAdapter<NewsModel, PagedListAdapter<NewsModel, NewsAdapter.MyViewHolder>>(NewsModel.D) {

}