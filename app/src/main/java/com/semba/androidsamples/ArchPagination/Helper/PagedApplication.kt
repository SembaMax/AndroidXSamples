package com.semba.androidsamples.ArchPagination.Helper

import android.app.Application
import com.semba.androidsamples.ArchPagination.Dagger.*

class PagedApplication: Application() {

    val utilsComponent: UtilsComponent by lazy {
        DaggerUtilsComponent
            .builder()
            .utilsModule(UtilsModule())
            .build()
    }

    override fun onCreate() {
        super.onCreate()
    }
}