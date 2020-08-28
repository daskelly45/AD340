package com.dave45.net.ad340.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.dave45.net.ad340.TempDisplaySettingManager
import com.dave45.net.ad340.databinding.FragmentForecastDetailsBinding
import com.dave45.net.ad340.formatIconIdToOpenWeatherIconUri
import com.dave45.net.ad340.formatTempForDisplay

class ForecastDetailsFragment : Fragment() {

    private val args: ForecastDetailsFragmentArgs by navArgs()
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    private var _binding: FragmentForecastDetailsBinding? = null
    // RThis property only valid between onCreateView and onDestroyView
    private val binding: FragmentForecastDetailsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForecastDetailsBinding.inflate(inflater, container, false)

        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        binding.tempText.text = formatTempForDisplay(args.temp, tempDisplaySettingManager.getTempDisplaySetting())
        binding.descriptionText.text = args.description

        binding.forecastIcon.load(formatIconIdToOpenWeatherIconUri(args.iconId))

        return binding.root
    }

    // Clean up _binding reference to ensure the _binding object is effectively removed from memory when the view is destroyed
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}