package com.dave45.net.ad340.api

import com.squareup.moshi.Json

data class Forecast(val temp: Float)
data class Coordinates(val lat: Float, val long: Float)

data class CurrentWeather(
    val name: String,
    val coord: Coordinates,
    @field:Json(name = "main") val forecast: Forecast
)