package com.dave45.net.ad340.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dave45.net.ad340.Location
import com.dave45.net.ad340.LocationRepository
import com.dave45.net.ad340.R
import com.dave45.net.ad340.databinding.FragmentLocationEntryBinding

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class LocationEntryFragment : Fragment() {

    private lateinit var locationRepository: LocationRepository

    private var _binding: FragmentLocationEntryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLocationEntryBinding.inflate(inflater, container, false)

        locationRepository = LocationRepository(requireContext())

        //update UI and get references
//        binding.zipcodeEditText.text.insert(0, "98101"
//        /**List(5) { Random.nextInt(0,9) }.joinToString("")*/
//            )

        binding.enterButton.setOnClickListener {
            val zipCode = binding.zipcodeEditText.text.toString()
            if(zipCode.length != 5)
                Toast.makeText(requireContext(), R.string.zipcode_entry_error, Toast.LENGTH_SHORT).show()
            else {
                locationRepository.saveLocation(Location.ZipCode(zipCode))
                // basically shows the forecast list fragment or whatever fragment is preceding this current one
                findNavController().navigateUp()
            }
//                appNavigator.navigateToCurrentForecast(zipCode)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}