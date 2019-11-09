package com.alex.weatherapp.network

import com.alex.weatherapp.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query
import io.reactivex.Observable

interface ApiServiceInterface {

    @GET("data/2.5/weather")
    fun getCurrentWeather(@Query("lat") lat: Double, @Query("lon") lon: Double, @Query("appid") appid: String, @Query("units") units: String): Observable<WeatherResponse>
}