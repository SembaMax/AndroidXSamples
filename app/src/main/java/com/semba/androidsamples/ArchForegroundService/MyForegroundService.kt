package com.semba.androidsamples.ArchForegroundService

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.semba.androidsamples.Helper.PagedApplication
import com.semba.androidsamples.Shared.Constants
import com.semba.androidsamples.Shared.NotificationManager
import org.koin.android.ext.android.inject

class MyForegroundService : Service() {

    val notificationManager: NotificationManager by inject()

    init {
        (applicationContext as PagedApplication).utilsComponent.inject(this)
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = notificationManager.makeStatusNotification("This is Foreground Service", "You are not able to close me !!!", applicationContext, false)
        startForeground(Constants.FOREGROUND_SERVICE_ID, notification)
        return START_REDELIVER_INTENT
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
