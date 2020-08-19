package com.dave45.net.ad340

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    // region Properties

    var times = 0
    private val forecastRepository = ForecastRepository()

    //endregion Properties

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val zipcodeEditText: EditText = findViewById(R.id.zipcodeEditText)
        zipcodeEditText.text.insert(0, List(5) { Random.nextInt(0,9) }.joinToString(""))


        val enterButton: Button = findViewById(R.id.enterButton)
        enterButton.setOnClickListener {
            val zipCode = zipcodeEditText.text.toString()
            if(zipCode.length != 5)
                Toast.makeText(this, R.string.zipcode_entry_error, Toast.LENGTH_SHORT).show()
            else
                forecastRepository.loadForecast(zipCode)
        }

        val forecastList: RecyclerView = findViewById(R.id.forecastList)
        forecastList.layoutManager = LinearLayoutManager(this)
        val dailyForecastAdapter = DailyForecastAdapter() { forecastItem ->
            val msg = getString(R.string.forecast_clicked_format, forecastItem.temp, forecastItem.description)
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
        forecastList.adapter = dailyForecastAdapter

        val weeklyForecastObserver = Observer<DailyForecasts> { forecastItems ->
            //update our list adapter
            dailyForecastAdapter.submitList(forecastItems)
        }

        forecastRepository.weeklyForecast.observe(this, weeklyForecastObserver)
    }
}