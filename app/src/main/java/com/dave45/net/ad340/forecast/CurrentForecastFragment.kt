package com.dave45.net.ad340.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.dave45.net.ad340.*
import com.dave45.net.ad340.api.CurrentWeather
import com.dave45.net.ad340.databinding.FragmentCurrentForecastBinding

/**
 * A simple [Fragment] subclass.
 * Use the [CurrentForecastFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CurrentForecastFragment : Fragment() {

    private val forecastRepository = ForecastRepository()
    private lateinit var locationRepository: LocationRepository
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
    private lateinit var binding: FragmentCurrentForecastBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        //val zipCode = arguments!!.getString(KEY_ZIPCODE) ?: ""
        val zipCode = arguments?.getString(KEY_ZIPCODE) ?: ""

        // Inflate the layout for this fragment
//        val view = inflater.inflate(R.layout.fragment_current_forecast, container, false)
        binding = FragmentCurrentForecastBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.locationEntryButton.setOnClickListener {
            showLocationEntry()
        }

        val currentWeatherObserver = Observer<CurrentWeather> { weather ->
            // update list adapter
            binding.locationName.text = weather.name
            binding.tempText.text = formatTempForDisplay(weather.forecast.temp, tempDisplaySettingManager.getTempDisplaySetting())
        }

        forecastRepository.currentWeather.observe(viewLifecycleOwner, currentWeatherObserver)

        locationRepository = LocationRepository(requireContext())
        val savedLocationObserver = Observer<Location> { savedLocation ->
            when(savedLocation) {
                is Location.ZipCode -> forecastRepository.loadCurrentForecast(savedLocation.zipCode)
            }
        }
        locationRepository.savedLocation.observe(viewLifecycleOwner, savedLocationObserver)

        return view
    }

    private fun showLocationEntry() {
        val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToLocationEntryFragment()
        findNavController().navigate(action)
    }

    private fun showForecastDetails(forecast: DailyForecast) {
        val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToForecastDetailsFragment(forecast.temp, forecast.description)
        findNavController().navigate(action)
    }

    companion object {
        const val KEY_ZIPCODE = "key_zipcode"

        fun newInstance(zipCode: String): CurrentForecastFragment {
            val fragment = CurrentForecastFragment()

            val args = Bundle()
            args.putString(KEY_ZIPCODE, zipCode)
            fragment.arguments = args

            return fragment
        }
    }
}