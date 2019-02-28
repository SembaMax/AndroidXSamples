package com.semba.androidsamples.ArchPagination.Dagger

import com.semba.androidsamples.ArchPagination.MainScreen.NewsActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component (modules = [UtilsModule::class])
interface UtilsComponent {
    fun inject(newsActivity: NewsActivity)
}