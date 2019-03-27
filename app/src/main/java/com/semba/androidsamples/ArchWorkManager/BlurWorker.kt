package com.semba.androidsamples.ArchWorkManager

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.text.TextUtils
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.semba.androidsamples.Helper.PagedApplication
import com.semba.androidsamples.Shared.Constants
import com.semba.androidsamples.Shared.NotificationManager
import java.lang.IllegalArgumentException
import javax.inject.Inject

class BlurWorker(private val context: Context, private val workerParams: WorkerParameters): Worker(context, workerParams) {

    @Inject
    lateinit var notificationManager: NotificationManager

    init {
        (context.applicationContext as PagedApplication).utilsComponent.inject(this)
    }

    override fun doWork(): Result {
        val resourceUri = inputData.getString(Constants.KEY_IMAGE_URI)
        try {
            //val pic = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.test)
            if (TextUtils.isEmpty(resourceUri))
                throw IllegalArgumentException("Invalid Uri")
            val resolver = applicationContext.contentResolver
            val pic = BitmapFactory.decodeStream(resolver.openInputStream(Uri.parse(resourceUri)))
            val output = WorkerUtils.blurBitmap(pic,applicationContext)
            val outputUri = WorkerUtils.writeBitmapToFile(applicationContext, output)
            notificationManager.makeStatusNotification("New Image","Output is " + outputUri.toString(), applicationContext)

            val outputData = Data.Builder()
                    .putString(Constants.KEY_IMAGE_URI, outputUri.toString())
                    .build()
            return Result.success(outputData)
        }
        catch (t: Throwable)
        {
            return Result.failure()
        }
    }
}