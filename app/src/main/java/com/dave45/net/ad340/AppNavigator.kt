package com.dave45.net.ad340

interface AppNavigator {
    fun navigateToCurrentForecast(zipCode: String)

    fun navigateToLocationEntry()

    fun navigateToForecastDetails(forecast: DailyForecast)
}