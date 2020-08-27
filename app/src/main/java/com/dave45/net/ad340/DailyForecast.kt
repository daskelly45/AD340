package com.dave45.net.ad340

import java.util.*

data class DailyForecast(
    val date: Date,
    val temp: Float,
    val description: String
)

typealias DailyForecasts = List<DailyForecast>