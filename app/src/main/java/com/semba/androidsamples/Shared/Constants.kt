package com.semba.androidsamples.Shared

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object Constants {
    val LOADING = "Loading"
    val LOADED = "Loaded"
    val CHECK_NETWORK_ERROR = "Check you network connection"
    val API_KEY = "a1b38c2fe3be4162a87347323851eb51"
    val sources = arrayListOf("")
    val NOTIFICATION_TITLE = "WorkRequest Starting"
    val CHANNEL_ID = "VERBOSE_NOTIFICATION"
    val NOTIFICATION_ID = 1
    val FOREGROUND_SERVICE_ID = 1

    val VERBOSE_NOTIFICATION_CHANNEL_NAME = "Verbose WorkManager Notifications"
    val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION = "Shows notifications whenever work starts"

    // The name of the image manipulation work
    val IMAGE_MANIPULATION_WORK_NAME = "image_manipulation_work"

    // Other keys
    val OUTPUT_PATH = "blur_filter_outputs"
    val KEY_IMAGE_URI = "KEY_IMAGE_URI"
    val TAG_OUTPUT = "OUTPUT"
    val DELAY_TIME_MILLIS: Long = 3000

    fun checkInternetConnection(context: Context): Boolean
    {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager == null)
        {
            return false
        }
        else
        {
            val info = connectivityManager.activeNetworkInfo
            if (info.isConnected)
            {
                return true
            }
        }
        return false
    }
}