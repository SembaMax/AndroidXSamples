package com.semba.androidsamples.Helper

import android.app.Application
import com.semba.androidsamples.Dagger.*
import com.semba.androidsamples.Koin.InjectionSample
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PagedApplication: Application() {

    val utilsComponent: UtilsComponent by lazy {
        DaggerUtilsComponent
            .builder()
            .utilsModule(UtilsModule())
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            //This module is injected in "MyForegroundService"
            androidContext(this@PagedApplication)
            modules(InjectionSample.notificationModule,InjectionSample.retrofitModule)
        }
    }
}