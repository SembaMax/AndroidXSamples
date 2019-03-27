/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.semba.androidsamples.ArchWorkManager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.semba.androidsamples.R
import com.semba.androidsamples.Shared.Constants

import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.UUID

internal object WorkerUtils {
    private val TAG = WorkerUtils::class.java.simpleName

    /**
     * Method for sleeping for a fixed about of time to emulate slower work
     */
    fun sleep() {
        try {
            Thread.sleep(Constants.DELAY_TIME_MILLIS, 0)
        } catch (e: InterruptedException) {
            Log.d(TAG, e.message)
        }

    }

    /**
     * Blurs the given Bitmap image
     * @param bitmap Image to blur
     * @param applicationContext Application context
     * @return Blurred bitmap image
     */
    @WorkerThread
    fun blurBitmap(
        bitmap: Bitmap,
        applicationContext: Context
    ): Bitmap {

        var rsContext: RenderScript? = null
        try {

            // Create the output bitmap
            val output = Bitmap.createBitmap(
                bitmap.width, bitmap.height, bitmap.config
            )

            // Blur the image
            rsContext = RenderScript.create(applicationContext, RenderScript.ContextType.DEBUG)
            val inAlloc = Allocation.createFromBitmap(rsContext, bitmap)
            val outAlloc = Allocation.createTyped(rsContext, inAlloc.type)
            val theIntrinsic = ScriptIntrinsicBlur.create(rsContext, Element.U8_4(rsContext))
            theIntrinsic.setRadius(10f)
            theIntrinsic.setInput(inAlloc)
            theIntrinsic.forEach(outAlloc)
            outAlloc.copyTo(output)

            return output
        } finally {
            rsContext?.finish()
        }
    }

    /**
     * Writes bitmap to a temporary file and returns the Uri for the file
     * @param applicationContext Application context
     * @param bitmap Bitmap to write to temp file
     * @return Uri for temp file with bitmap
     * @throws FileNotFoundException Throws if bitmap file cannot be found
     */
    @Throws(FileNotFoundException::class)
    fun writeBitmapToFile(
        applicationContext: Context,
        bitmap: Bitmap
    ): Uri {

        val name = String.format("blur-filter-output-%s.png", UUID.randomUUID().toString())
        val outputDir = File(applicationContext.filesDir, Constants.OUTPUT_PATH)
        if (!outputDir.exists()) {
            outputDir.mkdirs() // should succeed
        }
        val outputFile = File(outputDir, name)
        var out: FileOutputStream? = null
        try {
            out = FileOutputStream(outputFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* ignored for PNG */, out)
        } finally {
            if (out != null) {
                try {
                    out.close()
                } catch (ignore: IOException) {
                }

            }
        }
        return Uri.fromFile(outputFile)
    }
}