package com.alex.weatherapp.ui.main

import android.location.Location
import android.util.Log
import com.alex.weatherapp.network.ApiServiceInterface
import com.alex.weatherapp.common.util.APPID_KEY
import com.alex.weatherapp.common.util.GoogleLocationHelper
import com.alex.weatherapp.common.util.LocationListener
import com.alex.weatherapp.common.util.METRIC
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter @Inject constructor(private val apiServiceInterface: ApiServiceInterface, private val googleLocationHelper: GoogleLocationHelper) : MainContract.Presenter, LocationListener {

    private val subscriptions = CompositeDisposable()
    private lateinit var view: MainContract.View

    override fun subscribe() {

    }

    override fun unsubscribe() {

    }

    override fun attach(view: MainContract.View) {
        this.view = view
    }

    override fun getLocation() {
        googleLocationHelper.connect(this)
        Log.d(TAG, "Get Location")
    }

    override fun stopLocationUpdates() {
        googleLocationHelper.disconnect()
        Log.d(TAG, "Stop Location")
    }

    override fun onLocationChanged(location: Location) {
        Log.d(TAG, "Location has changed $location")

        view.showProgressDialog(true)

        var subscribe = apiServiceInterface.getCurrentWeather(location.latitude, location.longitude, APPID_KEY, METRIC).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( {
                view.showProgressDialog(false)
                view.displayWeatherData(it)
            }, { error ->
                view.showErrorMessage(error.localizedMessage)
            })
        subscriptions.add(subscribe)
    }

    companion object {
        private val TAG = "MainActivity"
    }
}