package com.dave45.net.ad340.forecast

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dave45.net.ad340.*
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [Fragment] subclass.
 * Use the [WeeklyForecastFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeeklyForecastFragment : Fragment() {

    private val forecastRepository = ForecastRepository()
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
    private lateinit var appNavigator: AppNavigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appNavigator  = context as AppNavigator
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        //val zipCode = arguments!!.getString(KEY_ZIPCODE) ?: ""
        val zipCode = arguments?.getString(CurrentForecastFragment.KEY_ZIPCODE) ?: ""

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_weekly_forecast, container, false)

        val locationEntryButton: FloatingActionButton = view.findViewById(R.id.locationEntryButton)
        locationEntryButton.setOnClickListener {
            appNavigator.navigateToLocationEntry()
        }

        val forecastList: RecyclerView = view.findViewById(R.id.forecastList)
        forecastList.layoutManager = LinearLayoutManager(requireContext())
        val dailyForecastAdapter = DailyForecastAdapter(tempDisplaySettingManager) { forecastItem ->
            showForecastDetails(forecastItem)
        }
        forecastList.adapter = dailyForecastAdapter

        val weeklyForecastObserver = Observer<DailyForecasts> { forecastItems ->
            //update our list adapter
            dailyForecastAdapter.submitList(forecastItems)
        }

        forecastRepository.weeklyForecast.observe(this, weeklyForecastObserver)

        forecastRepository.loadForecast(zipCode)

        return view
    }


    private fun showForecastDetails(forecast: DailyForecast) {
        appNavigator.navigateToForecastDetails(forecast)
    }

    companion object {
        const val KEY_ZIPCODE = "key_zipcode"

        fun newInstance(zipCode: String): WeeklyForecastFragment {
            val fragment = WeeklyForecastFragment()

            val args = Bundle()
            args.putString(KEY_ZIPCODE, zipCode)
            fragment.arguments = args

            return fragment
        }
    }
}