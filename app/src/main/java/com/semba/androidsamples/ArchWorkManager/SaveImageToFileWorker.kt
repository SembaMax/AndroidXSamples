package com.semba.androidsamples.ArchWorkManager

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat
import java.util.*

class SaveImageToFileWorker(context: Context, workerParams: WorkerParameters): Worker(context,workerParams) {

    private val TAG = this.javaClass.simpleName
    private val TITLE = "Blurred Image"
    private val DATE_FORMATTER = SimpleDateFormat("yyy.MM.dd 'at' HH:mm:ss z", Locale.getDefault())

    override fun doWork(): Result {
        val resolver = applicationContext.contentResolver
        try {
            val resourceUri = ""
            val bitmap = BitmapFactory.decodeStream(resolver.openInputStream(Uri.parse(resourceUri)))
            val outputUri = MediaStore.Images.Media.insertImage(resolver,bitmap,TITLE,DATE_FORMATTER.format(Date()))
            if (outputUri.isEmpty())
                return Result.failure()

            return Result.success()
        }
        catch (t: Throwable)
        {
            return Result.failure()
        }
    }
    
}