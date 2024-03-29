package com.alex.weatherapp.model

data class WeatherResponse(
    val id: Int,
    val main: Main,
    val name: String,
    val weather: List<Weather>
)