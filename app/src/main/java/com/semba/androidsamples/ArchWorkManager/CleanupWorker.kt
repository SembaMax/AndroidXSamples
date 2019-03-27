package com.semba.androidsamples.ArchWorkManager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.semba.androidsamples.Shared.Constants
import java.io.File

class CleanupWorker(context: Context, workerParams: WorkerParameters): Worker(context,workerParams) {

    private val TAG = this.javaClass.simpleName

    override fun doWork(): Result {
        try {
            val outputDirectory = File(applicationContext.filesDir, Constants.OUTPUT_PATH)
            if (outputDirectory.exists())
            {
                val entries = outputDirectory.listFiles()
                if (entries.isNotEmpty())
                {
                    for (entry in entries)
                    {
                        val name = entry.name
                        if (name.isNotEmpty() && name.endsWith(".png"))
                        {
                            val deleted = entry.delete()
                            Log.i(TAG, String.format("Deleted %s - %s", name, deleted))
                        }
                    }
                }
            }
            return Result.success()
        }
        catch (t: Throwable)
        {
            return Result.failure()
        }
    }
}