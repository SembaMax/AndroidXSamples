package com.semba.androidsamples.ArchWorkManager

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.work.*
import com.semba.androidsamples.Shared.Constants

class MyWorkManager {

    private var mImageUri: Uri? = null
    private var workManager: WorkManager? = null
    private var mSavedWorkInfo: LiveData<List<WorkInfo>>? = null
    private var mOutputUri: Uri? = null

    init {
        workManager = WorkManager.getInstance()
        mSavedWorkInfo = workManager?.getWorkInfosByTagLiveData(Constants.TAG_OUTPUT)
    }

    fun setupImageBluringWork(blurLevel: Int)
    {
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val workContinuation = workManager?.beginUniqueWork(
                Constants.IMAGE_MANIPULATION_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.from(CleanupWorker::class.java)
            )
        for (i in 0 until blurLevel) {
            val blurWork = OneTimeWorkRequest.Builder(BlurWorker::class.java)

            if (i == 0)
                blurWork.setInputData(createInputDataForUri())

            workContinuation?.then(blurWork.build())
        }

        val saveWork = OneTimeWorkRequest.Builder(SaveImageToFileWorker::class.java)
            .addTag(Constants.TAG_OUTPUT)
            .build()

        workContinuation?.then(saveWork)
        workContinuation?.enqueue()
    }

    private fun createInputDataForUri(): Data {
        val builder = Data.Builder()
        if (mImageUri != null) {
            builder.putString(Constants.KEY_IMAGE_URI, mImageUri.toString())
        }
        return builder.build()
    }
}