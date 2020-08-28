package com.dave45.net.ad340.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dave45.net.ad340.*
import com.dave45.net.ad340.api.DailyForecast
import com.dave45.net.ad340.api.WeeklyForecast
import com.dave45.net.ad340.databinding.FragmentWeeklyForecastBinding

/**
 * A simple [Fragment] subclass.
 * Use the [WeeklyForecastFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeeklyForecastFragment : Fragment() {

    private val forecastRepository = ForecastRepository()
    private lateinit var locationRepository: LocationRepository
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
    private lateinit var binding: FragmentWeeklyForecastBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        val zipCode = arguments?.getString(CurrentForecastFragment.KEY_ZIPCODE) ?: ""

        // Inflate the layout for this fragment
        binding = FragmentWeeklyForecastBinding.inflate(inflater, container, false)

        binding.locationEntryButton.setOnClickListener {
            showLocationEntry()
        }

        val forecastList = binding.forecastList
        forecastList.layoutManager = LinearLayoutManager(requireContext())
        val dailyForecastAdapter = DailyForecastListAdapter(tempDisplaySettingManager) { forecastItem ->
            showForecastDetails(forecastItem)
        }
        forecastList.adapter = dailyForecastAdapter

        val weeklyForecastObserver = Observer<WeeklyForecast> { weeklyForecast ->
            //update our list adapter
            dailyForecastAdapter.submitList(weeklyForecast.daily)
        }

        forecastRepository.weeklyForecast.observe(viewLifecycleOwner, weeklyForecastObserver)

        locationRepository = LocationRepository(requireContext())
        val savedLocationObserver = Observer<Location> { savedLocation ->
            when(savedLocation) {
                is Location.ZipCode -> forecastRepository.loadWeeklyForecast(savedLocation.zipCode)
            }
        }
        locationRepository.savedLocation.observe(viewLifecycleOwner, savedLocationObserver)

        return binding.root
    }

    private fun showLocationEntry() {
        val action = WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToLocationEntryFragment()
        findNavController().navigate(action)
    }

    private fun showForecastDetails(forecast: DailyForecast) {
        val temp = forecast.temp.max
        val description = forecast.weather.first().description
        val action = WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToForecastDetailsFragment(temp, description, forecast.weather.first().icon)
        findNavController().navigate(action)
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