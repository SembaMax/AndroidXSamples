package com.semba.androidsamples.ArchPagination.MainScreen.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.semba.androidsamples.ArchPagination.Data.Models.NewsModel

class NewsAdapter: PagedListAdapter<NewsModel, NewsAdapter.MyViewHolder>(NewsModel.DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsAdapter.MyViewHolder {
        //val row = DataBindingUtil.inflate<NewsRowLayoutBinding>(LayoutInflater.from(parent.context), R.layout.news_row_layout, parent, false)
        return MyViewHolder(parent)
    }

    override fun onBindViewHolder(holder: NewsAdapter.MyViewHolder, position: Int) {
        //holder.row.model = getItem(position)
    }

    override fun getItem(position: Int): NewsModel? {
        return super.getItem(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    //inner class MyViewHolder(val row: NewsRowLayoutBinding): RecyclerView.ViewHolder(row.root)
    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view)
}