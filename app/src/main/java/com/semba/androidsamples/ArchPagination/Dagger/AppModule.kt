package com.semba.androidsamples.ArchPagination.Dagger

import com.semba.androidsamples.ArchPagination.Helper.PagedApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: PagedApplication) {

    @Provides
    @Singleton
    fun provideApp() = app
}