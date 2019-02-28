package com.semba.androidsamples.ArchPagination.MainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.semba.androidsamples.ArchPagination.Data.Repository
import com.semba.androidsamples.Shared.Logger
import java.lang.IllegalArgumentException
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val repository: Repository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(NewsScreenViewModel::class.java))
        {
            NewsScreenViewModel(this.repository) as T
        }
        else
        {
            val msg = "ViewModel not found"
            Logger().e(msg)
            throw IllegalArgumentException(msg)
        }
    }
}