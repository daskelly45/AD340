package com.dave45.net.ad340

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

class ForecastRepository {

    private val  _weeklyForecast = MutableLiveData<List<DailyForecast>>()
    val weeklyForecast: LiveData<List<DailyForecast>> = _weeklyForecast

    fun loadForecast(zipCode: String) {
        val randomValues = List(10) { Random.nextFloat().rem(100) * 100 }
        val forecastItems = randomValues.map { temp ->
            DailyForecast(temp, getItemDescription(temp))
        }
        _weeklyForecast.value = forecastItems
    }

    private fun getItemDescription(temp: Float): String {
        return when(temp) {
            in Float.MIN_VALUE..0f -> "Anything below doesn't make sense"
            in 0f..32f -> "Way too cold"
            in 32f..55f -> "Colder than i would prefer"
            in 55f..65f -> "Getting better"
            in 65f..80f -> "That's the sweet spot"
            in 80f..90f -> "Getting a little warm"
            in 90f..100f -> "Where's the A/C?"
            in 100f..Float.MAX_VALUE -> "What is this, Arizona ?"
            else -> "Does not compute"
        }
    }
}