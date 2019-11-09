package com.alex.weatherapp.ui.main

import com.alex.weatherapp.ui.base.BaseContract
import com.alex.weatherapp.model.WeatherResponse

class MainContract {


    interface View {
        fun showProgressDialog(show: Boolean)
        fun showErrorMessage(error: String?)
        fun displayWeatherData(response: WeatherResponse)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun getLocation()
        fun stopLocationUpdates()
    }
}