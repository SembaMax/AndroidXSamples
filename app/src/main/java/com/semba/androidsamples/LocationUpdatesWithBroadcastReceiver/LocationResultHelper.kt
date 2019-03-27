/*
 * Copyright (C) 2017 The Android Open Source Project
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

package com.semba.androidsamples.LocationUpdatesWithBroadcastReceiver

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.preference.PreferenceManager
import com.semba.androidsamples.R


import java.text.DateFormat
import java.util.Date
import javax.inject.Inject

/**
 * Class to process location results.
 */
internal class LocationResultHelper(private val mContext: Context, private val mLocations: List<Location>) {

    /**
     * Returns the title for reporting about a list of [Location] objects.
     */

    val locationResultTitle: String
        get() {
            val numLocationsReported = mContext.resources.getQuantityString(
                R.plurals.num_locations_reported, mLocations.size, mLocations.size
            )
            return numLocationsReported + ": " + DateFormat.getDateTimeInstance().format(Date())
        }

    val locationResultText: String
        get() {
            if (mLocations.isEmpty()) {
                return mContext.getString(R.string.unknown_location)
            }
            val sb = StringBuilder()
            for (location in mLocations) {
                sb.append("(")
                sb.append(location.latitude)
                sb.append(", ")
                sb.append(location.longitude)
                sb.append(")")
                sb.append("\n")
            }
            return sb.toString()
        }

    /**
     * Saves location result as a string to [android.content.SharedPreferences].
     */
    fun saveResults() {
        PreferenceManager.getDefaultSharedPreferences(mContext)
            .edit()
            .putString(
                KEY_LOCATION_UPDATES_RESULT, locationResultTitle + "\n" +
                        locationResultText
            )
            .apply()
    }

    companion object {

        val KEY_LOCATION_UPDATES_RESULT = "location-update-result"

        private val PRIMARY_CHANNEL = "default"

        /**
         * Fetches location results from [android.content.SharedPreferences].
         */
        fun getSavedLocationResult(context: Context): String? {
            return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(KEY_LOCATION_UPDATES_RESULT, "")
        }
    }
}
