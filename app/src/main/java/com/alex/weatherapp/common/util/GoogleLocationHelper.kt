package com.alex.weatherapp.common.util

import android.content.Context
import android.os.Bundle
import android.os.Looper
import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*

class GoogleLocationHelper(context: Context): GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    lateinit var locationListener: LocationListener
    private var googleApiClient: GoogleApiClient = GoogleApiClient.Builder(context)
    .addApi(LocationServices.API)
    .addConnectionCallbacks(this)
    .addOnConnectionFailedListener(this).build()
    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    fun connect(_locationListener: LocationListener) {
        Log.d(TAG, "connect")
        locationListener = _locationListener
        googleApiClient.connect()
    }

    fun disconnect() {
        Log.d(TAG, "disconnect")
        fusedLocationClient.removeLocationUpdates(object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                for (location in locationResult.locations){
                    locationListener?.onLocationChanged(location)
                }
            }
        })
        googleApiClient.disconnect()
    }

    override fun onConnected(bundle: Bundle?) {
        fusedLocationClient.lastLocation.addOnSuccessListener {location ->
            Log.d(TAG, "getLastKnownLocation onSuccess $location")
            if(location != null) {
                locationListener?.onLocationChanged(location)
                Log.d(TAG, "location is $location")
            }else {
                requestLocationUpdates()
            }

        }
    }

    private fun requestLocationUpdates() {
        val locationRequest = LocationRequest.create()?.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                for (location in locationResult.locations){
                    locationListener?.onLocationChanged(location)
                }
            }
        }, Looper.getMainLooper())
    }

    override fun onConnectionSuspended(i: Int) {

    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {

    }

    companion object {
        private const val TAG = "GoogleLocationHelper"
    }

}