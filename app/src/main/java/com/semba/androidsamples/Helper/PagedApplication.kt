package com.semba.androidsamples.Helper

import android.app.Application
import com.semba.androidsamples.Dagger.*

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