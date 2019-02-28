package com.semba.androidsamples.ArchPagination.Data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object Constants {
    val LOADING = "Loading"
    val LOADED = "Loaded"
    val CHECK_NETWORK_ERROR = "Check you network connection"
    val API_KEY = "a1b38c2fe3be4162a87347323851eb51"
    val sources = arrayListOf("")

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