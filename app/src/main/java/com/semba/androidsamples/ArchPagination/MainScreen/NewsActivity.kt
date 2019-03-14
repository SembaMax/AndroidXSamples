package com.semba.androidsamples.ArchPagination.MainScreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.semba.androidsamples.ArchPagination.Helper.PagedApplication
import com.semba.androidsamples.R
import kotlinx.android.synthetic.main.activity_news.*
import retrofit2.Retrofit
import javax.inject.Inject

class NewsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var retrofit: Retrofit

    private var viewModel: NewsScreenViewModel? = null
    private var adapter:

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        (application as PagedApplication).utilsComponent.inject(this)
        viewModel = viewModelFactory.create(NewsScreenViewModel::class.java)
        init()
    }

    private fun init()
    {
        news_recyclerView?.layoutManager = LinearLayoutManager(this)

    }
}
