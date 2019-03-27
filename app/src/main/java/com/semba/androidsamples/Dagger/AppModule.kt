package com.semba.androidsamples.Dagger

import com.semba.androidsamples.Helper.PagedApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: PagedApplication) {

    @Provides
    @Singleton
    fun provideApp() = app
}