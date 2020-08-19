package com.dave45.net.ad340.details

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dave45.net.ad340.*

class ForecastDetailsActivity : AppCompatActivity() {

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast_details)

        tempDisplaySettingManager = TempDisplaySettingManager(this)

        setTitle(R.string.forecast_details)

        val tempText: TextView = findViewById(R.id.temp_text)
        val descriptionText: TextView = findViewById(R.id.description_text)

        val temp = intent.getFloatExtra("key_temp", 0f)
        tempText.text = formatTempForDisplay(temp, tempDisplaySettingManager.getTempDisplaySetting())
        descriptionText.text = intent.getStringExtra("key_description")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.tempDisplaySetting -> {
                showTempDisplaySettingDialog(this, tempDisplaySettingManager)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}