package com.alex.weatherapp.common.util

import android.location.Location

interface LocationListener {
    fun onLocationChanged(location: Location)
}