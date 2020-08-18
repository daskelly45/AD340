package com.dave45.net.ad340

data class DailyForecast(
    val temp: Float,
    val description: String
)

typealias DailyForecasts = List<DailyForecast>