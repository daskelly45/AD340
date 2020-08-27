package com.dave45.net.ad340

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dave45.net.ad340.api.CurrentWeather
import com.dave45.net.ad340.api.WeeklyForecast
import com.dave45.net.ad340.api.createOpenWeatherMapService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class ForecastRepository {

    private val _currentWeather = MutableLiveData<CurrentWeather>()
    val currentWeather: LiveData<CurrentWeather> = _currentWeather

    private val  _weeklyForecast = MutableLiveData<WeeklyForecast>()
    val weeklyForecast: LiveData<WeeklyForecast> = _weeklyForecast

    fun loadWeeklyForecast(zipCode: String) {
        val call = createOpenWeatherMapService().currentWeather(zipCode, "imperial", BuildConfig.OPEN_WEATHER_MAP_API_KEY)
        call.enqueue(object : Callback<CurrentWeather> {
            override fun onResponse(
                call: Call<CurrentWeather>,
                response: Response<CurrentWeather>
            ) {
                val weatherResponse = response.body()
                if (weatherResponse != null) {
                    // load 7 day forecast
                    val forecastCall = createOpenWeatherMapService().sevenDayForecast(
                        lat = weatherResponse.coord.lat,
                        lon = weatherResponse.coord.long,
                        exclude = "current,minutely,hourly",
                        units = "imperial",
                        apiKey = BuildConfig.OPEN_WEATHER_MAP_API_KEY
                    )

                    forecastCall.enqueue(object : Callback<WeeklyForecast> {
                        override fun onResponse(
                            call: Call<WeeklyForecast>,
                            response: Response<WeeklyForecast>
                        ) {
                            val weeklyForecastResponse = response.body()
                            if(weeklyForecastResponse != null) {
                                _weeklyForecast.value = weeklyForecastResponse
                            }
                        }

                        override fun onFailure(call: Call<WeeklyForecast>, t: Throwable) {
                            Log.e(ForecastRepository::class.java.simpleName, "Error loading weekly forecast", t)
                        }
                    })
                }
            }

            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.e(
                    ForecastRepository::class.java.simpleName,
                    "Error loading location for weekly forecast",
                    t
                )
            }
        })
    }

    fun loadCurrentForecast(zipCode: String) {
        val call = createOpenWeatherMapService().currentWeather(zipCode, "imperial", BuildConfig.OPEN_WEATHER_MAP_API_KEY)
        call.enqueue(object : Callback<CurrentWeather> {
            override fun onResponse(
                call: Call<CurrentWeather>,
                response: Response<CurrentWeather>
            ) {
                val weatherResponse = response.body()
                if(weatherResponse != null)
                    _currentWeather.value = weatherResponse
            }

            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.e(ForecastRepository::class.java.simpleName, "Error loading current weather", t)
            }
        })
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

    private fun randomTemp() = Random.nextFloat().rem(100) * 100

//    private fun randomForecast(date: Date = Date()): DailyForecast {
//        val temps = List(2) { randomTemp() }.sortedDescending()
//        val description = getItemDescription(temps.first())
//        return DailyForecast(date.time, Temp(temps.last(), temps.first()), description)
//    }
}