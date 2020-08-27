package com.dave45.net.ad340

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dave45.net.ad340.api.DailyForecast
import com.dave45.net.ad340.databinding.ItemDailyForecastBinding
import java.text.SimpleDateFormat
import java.util.*


private val DATE_FORMAT = SimpleDateFormat("MM-dd-yyyy")

class DailyForecastViewHolder(
    view: View,
    private val tempDisplaySettingManager: TempDisplaySettingManager
) : RecyclerView.ViewHolder(view) {

    private val binding = ItemDailyForecastBinding.bind(view)

    fun bind(dailyForecast: DailyForecast) {
        binding.tempText.text = formatTempForDisplay(dailyForecast.temp.max, tempDisplaySettingManager.getTempDisplaySetting())
        binding.descriptionText.text = dailyForecast.weather.first().description
        binding.dateText.text = DATE_FORMAT.format(Date(dailyForecast.date * 1000))

        val iconId = dailyForecast.weather.first().icon
        binding.forecastIcon.load("http://openweathermap.org/img/wn/${iconId}@2x.png")
    }
}

class DailyForecastListAdapter(
    private val tempDisplaySettingManager: TempDisplaySettingManager,
    private val clickHandler: (DailyForecast) -> Unit
): ListAdapter<DailyForecast, DailyForecastViewHolder>(DIFF_CONFIG) {



    companion object {
        val DIFF_CONFIG = object : DiffUtil.ItemCallback<DailyForecast>() {

            override fun areItemsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean = oldItem === newItem

            override fun areContentsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        val itemView = ItemDailyForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
//            LayoutInflater.from(parent.context).inflate(R.layout.item_daily_forecast, parent, false)
        return DailyForecastViewHolder(itemView, tempDisplaySettingManager)
    }

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { clickHandler(getItem(position)) }
    }
}