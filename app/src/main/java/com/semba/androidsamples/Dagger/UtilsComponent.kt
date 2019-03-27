package com.semba.androidsamples.Dagger

import com.semba.androidsamples.ArchForegroundService.MyForegroundService
import com.semba.androidsamples.ArchPagination.MainScreen.NewsActivity
import com.semba.androidsamples.ArchWorkManager.BlurWorker
import com.semba.androidsamples.LocationUpdatesWithBroadcastReceiver.LocationUpdatesBroadcastReceiver
import com.semba.androidsamples.LocationUpdatesWithBroadcastReceiver.LocationUpdatesIntentService
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component (modules = [UtilsModule::class])
interface UtilsComponent {
    fun inject(newsActivity: NewsActivity)
    fun inject(locationUpdatesIntentService: LocationUpdatesIntentService)
    fun inject(locationUpdatesBroadcastReceiver: LocationUpdatesBroadcastReceiver)
    fun inject(blurWorker: BlurWorker)
    fun inject(myForegroundService: MyForegroundService)
}