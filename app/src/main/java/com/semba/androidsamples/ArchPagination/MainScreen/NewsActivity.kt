package com.semba.androidsamples.ArchPagination.MainScreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.semba.androidsamples.ArchPagination.Helper.PagedApplication
import com.semba.androidsamples.R
import javax.inject.Inject

class NewsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var viewModel: NewsScreenViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        (application as PagedApplication).appComponent.inject()
    }
}
