package com.semba.androidsamples.LocationUpdatesWithBroadcastReceiver

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.semba.androidsamples.BuildConfig
import com.semba.androidsamples.R

class LocationTrackerBroadcastActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private val TAG = this::class.java.simpleName
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    // FIXME: 5/16/17
    private val UPDATE_INTERVAL = (10 * 1000).toLong()

    /**
     * The fastest rate for active location updates. Updates will never be more frequent
     * than this value, but they may be less frequent.
     */
    // FIXME: 5/14/17
    private val FASTEST_UPDATE_INTERVAL = UPDATE_INTERVAL / 2

    /**
     * The max time before batched results are delivered by location services. Results may be
     * delivered sooner than this interval.
     */
    private val MAX_WAIT_TIME = UPDATE_INTERVAL * 3

    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    private var mLocationRequest: LocationRequest? = null

    /**
     * The entry point to Google Play Services.
     */
    private var mGoogleApiClient: GoogleApiClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_tracker_broadcast)

        if (!checkPermissions()) {
            requestPermissions()
        }

        buildGoogleApiClient()
    }

    override fun onStart() {
        super.onStart()
        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(this)
    }


    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        PreferenceManager.getDefaultSharedPreferences(this)
            .unregisterOnSharedPreferenceChangeListener(this)
        super.onStop()
    }

    private fun createLocationRequest() {
        mLocationRequest = LocationRequest()
        mLocationRequest?.setInterval(UPDATE_INTERVAL)
        mLocationRequest?.setFastestInterval(FASTEST_UPDATE_INTERVAL)
        mLocationRequest?.setMaxWaitTime(MAX_WAIT_TIME)
        mLocationRequest?.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
    }

    private fun buildGoogleApiClient() {
        if (mGoogleApiClient != null) {
            return
        }
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .enableAutoManage(this, this)
            .addApi(LocationServices.API)
            .build()
        createLocationRequest()
    }

    override fun onConnected(p0: Bundle?) {
        Log.i(TAG, "GoogleApiClient connected")
    }

    private fun getPendingIntent(): PendingIntent {
        val intent = Intent(this, LocationUpdatesBroadcastReceiver::class.java)
        intent.setAction(LocationUpdatesBroadcastReceiver.ACTION_PROCESS_UPDATES)
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    override fun onConnectionSuspended(i: Int) {
        val text = "Connection suspended"
        Log.w(TAG, "$text: Error code: $i")
        showSnackbar("Connection suspended")
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        val text = "Exception while connecting to Google Play services"
        Log.w(TAG, text + ": " + connectionResult.getErrorMessage())
        showSnackbar(text)
    }

    private fun showSnackbar(text: String) {
        val container = findViewById<ConstraintLayout>(R.id.activity_location_tracker_broadcast)
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")
            Snackbar.make(
                findViewById<ConstraintLayout>(R.id.activity_location_tracker_broadcast),
                R.string.permission_rationale,
                Snackbar.LENGTH_INDEFINITE
            )
                .setAction(R.string.ok, View.OnClickListener {
                    // Request permission
                    ActivityCompat.requestPermissions(
                        this@LocationTrackerBroadcastActivity,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_PERMISSIONS_REQUEST_CODE
                    )
                })
                .show()
        } else {
            Log.i(TAG, "Requesting permission")
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(
                this@LocationTrackerBroadcastActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_PERMISSIONS_REQUEST_CODE
            )
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
         grantResults: IntArray
    ) {
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.size <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.")
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted. Kick off the process of building and connecting
                // GoogleApiClient.
                buildGoogleApiClient()
            } else {
                // Permission denied.

                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                Snackbar.make(
                    findViewById<ConstraintLayout>(R.id.activity_location_tracker_broadcast),
                    R.string.permission_denied_explanation,
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction(R.string.settings, View.OnClickListener {
                        // Build intent that displays the App settings screen.
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts(
                            "package",
                            BuildConfig.APPLICATION_ID, null
                        )
                        intent.data = uri
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    })
                    .show()
            }
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, s: String) {
        if (s == LocationResultHelper.KEY_LOCATION_UPDATES_RESULT) {

        } else if (s == LocationRequestHelper.KEY_LOCATION_UPDATES_REQUESTED) {

        }
    }

    /**
     * Handles the Request Updates button and requests start of location updates.
     */
    fun requestLocationUpdates(view: View) {
        try {
            Log.i(TAG, "Starting location updates")
            LocationRequestHelper.setRequesting(this, true)
            LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, getPendingIntent()
            )
        } catch (e: SecurityException) {
            LocationRequestHelper.setRequesting(this, false)
            e.printStackTrace()
        }

    }

    /**
     * Handles the Remove Updates button, and requests removal of location updates.
     */
    fun removeLocationUpdates(view: View) {
        Log.i(TAG, "Removing location updates")
        LocationRequestHelper.setRequesting(this, false)
        LocationServices.FusedLocationApi.removeLocationUpdates(
            mGoogleApiClient,
            getPendingIntent()
        )
    }
}
