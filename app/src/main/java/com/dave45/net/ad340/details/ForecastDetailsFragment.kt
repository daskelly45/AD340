package com.dave45.net.ad340.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.dave45.net.ad340.TempDisplaySettingManager
import com.dave45.net.ad340.databinding.FragmentForecastDetailsBinding
import com.dave45.net.ad340.formatTempForDisplay

class ForecastDetailsFragment : Fragment() {

    private val args: ForecastDetailsFragmentArgs by navArgs()
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
    private lateinit var binding: FragmentForecastDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForecastDetailsBinding.inflate(inflater, container, false)

        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        binding.tempText.text = formatTempForDisplay(args.temp, tempDisplaySettingManager.getTempDisplaySetting())
        binding.descriptionText.text = args.description

        return binding.root
    }
}