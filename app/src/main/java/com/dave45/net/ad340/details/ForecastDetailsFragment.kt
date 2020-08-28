package com.dave45.net.ad340.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import coil.load
import com.dave45.net.ad340.TempDisplaySettingManager
import com.dave45.net.ad340.databinding.FragmentForecastDetailsBinding
import com.dave45.net.ad340.formatTempForDisplay

class ForecastDetailsFragment : Fragment() {

    private val args: ForecastDetailsFragmentArgs by navArgs()

    private lateinit var viewModelFactory: ForecastDetailsViewModelFactory
    private val viewModel: ForecastDetailsViewModel by viewModels(
        factoryProducer = { viewModelFactory }
    )

    private var _binding: FragmentForecastDetailsBinding? = null
    // RThis property only valid between onCreateView and onDestroyView
    private val binding: FragmentForecastDetailsBinding get() = _binding!!

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForecastDetailsBinding.inflate(inflater, container, false)
        viewModelFactory = ForecastDetailsViewModelFactory(args)
        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewStateObserver = Observer<ForecastDetailsViewState> { viewState ->
            // update the UI
            binding.tempText.text = formatTempForDisplay(viewState.temp, tempDisplaySettingManager.getTempDisplaySetting())
            binding.descriptionText.text = viewState.description
            binding.dateText.text = viewState.date
            binding.forecastIcon.load(viewState.iconUrl)
        }
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
    }

    // Clean up _binding reference to ensure the _binding object is effectively removed from memory when the view is destroyed
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}