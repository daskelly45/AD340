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

    private var _binding: FragmentCurrentForecastBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCurrentForecastBinding.inflate(inflater, container, false)

        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        val zipCode = arguments?.getString(KEY_ZIPCODE) ?: ""

        binding.locationEntryButton.setOnClickListener {
            showLocationEntry()
        }

        val currentWeatherObserver = Observer<CurrentWeather> { weather ->
            binding.emptyText.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
            binding.locationName.visibility = View.VISIBLE
            binding.tempText.visibility = View.VISIBLE

            binding.locationName.text = weather.name
            binding.tempText.text = formatTempForDisplay(weather.forecast.temp, tempDisplaySettingManager.getTempDisplaySetting())
        }

        forecastRepository.currentWeather.observe(viewLifecycleOwner, currentWeatherObserver)

        locationRepository = LocationRepository(requireContext())
        val savedLocationObserver = Observer<Location> { savedLocation ->
            when(savedLocation) {
                is Location.ZipCode -> {
                    binding.progressBar.visibility = View.VISIBLE
                    forecastRepository.loadCurrentForecast(savedLocation.zipCode)
                }
            }
        }
        locationRepository.savedLocation.observe(viewLifecycleOwner, savedLocationObserver)

        return binding.root
    }

    private fun showLocationEntry() {
        val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToLocationEntryFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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