package com.semba.androidsamples.ArchPagination.Helper

import android.app.Application
import com.semba.androidsamples.ArchPagination.Dagger.AppComponent
import com.semba.androidsamples.ArchPagination.Dagger.AppModule
import com.semba.androidsamples.ArchPagination.Dagger.DaggerAppComponent
import com.semba.androidsamples.ArchPagination.Dagger.UtilsModule

class PagedApplication: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .utilsModule(UtilsModule())
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }
}