package com.semba.androidsamples.ArchPagination.MainScreen

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.semba.androidsamples.ArchPagination.Data.Repository
import com.semba.androidsamples.Shared.Logger
import java.lang.IllegalArgumentException
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val repository: Repository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>) = with(modelClass) {
        when {
            isAssignableFrom(NewsScreenViewModel::class.java) ->
                NewsScreenViewModel()
            else-> {
                val msg = "Unknown ViewModel Class : ${modelClass.name}"
                Logger().e(msg)
                throw IllegalArgumentException(msg)
            }
        } as T
    }
}