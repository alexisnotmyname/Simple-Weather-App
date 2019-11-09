package com.alex.weatherapp.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.alex.weatherapp.ui.base.BaseApplication
import com.alex.weatherapp.R
import com.alex.weatherapp.common.util.WEATHER_ICON_URL
import com.alex.weatherapp.model.WeatherResponse
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainContract.View {

    @Inject lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        injectDependency()
        presenter.attach(this)
    }

    override fun onStart() {
        super.onStart()
        if (!checkPermissions()) {
            requestPermissions()
        } else {
            presenter.getLocation()
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
        presenter.stopLocationUpdates()
    }

    private fun injectDependency() {
        (application as BaseApplication).component.inject(this)
    }

    private fun checkPermissions(): Boolean {
        val permissionStateCoarse = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        val permissionStateFine = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        return permissionStateCoarse == PackageManager.PERMISSION_GRANTED && permissionStateFine == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {

        ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            when {
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                    presenter.getLocation()
                }
                else -> {
                    Toast.makeText(applicationContext, getString(R.string.permission_denied_explanation), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun showProgressDialog(show: Boolean) {
        if(show) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }

    }

    override fun showErrorMessage(error: String) {

    }

    @SuppressLint("SetTextI18n")
    override fun displayWeatherData(response: WeatherResponse) {
        txt_location_name.text = response.name
        txt_temp.text = " ${response.main.temp} ${0x00B0.toChar()}"
        txt_cloud.text = response.weather[0].description

        val iconUrl = "$WEATHER_ICON_URL/${response.weather[0].icon}@2x.png"

        Log.d(TAG, iconUrl)
        loadWeatherIcon(iconUrl)
    }

    private fun loadWeatherIcon(iconUrl: String) {
        Glide.with(this)
            .load(iconUrl)
            .placeholder(R.drawable.placeholder_sun)
            .error(R.drawable.placeholder_sun)
            .transform(CircleCrop())
            .into(img_weather_icon)

    }

    companion object {

        private val TAG = "MainActivity"
        private const val REQUEST_PERMISSIONS_REQUEST_CODE = 1000
    }

}
