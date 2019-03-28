package com.semba.androidsamples.Koin

import com.semba.androidsamples.API.RetrofitWork
import com.semba.androidsamples.Shared.NotificationManager
import org.koin.core.module.Module
import org.koin.dsl.module

class InjectionSample {

    companion object {
        val notificationModule: Module = module {
            single { NotificationManager() }
        }

        val retrofitModule: Module = module {
            single { RetrofitWork.getApiCallInterface() }
        }
    }
}