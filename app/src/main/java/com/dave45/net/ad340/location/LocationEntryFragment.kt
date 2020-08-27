package com.dave45.net.ad340.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dave45.net.ad340.Location
import com.dave45.net.ad340.LocationRepository
import com.dave45.net.ad340.R
import kotlin.random.Random

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class LocationEntryFragment : Fragment() {

    private lateinit var locationRepository: LocationRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        locationRepository = LocationRepository(requireContext())

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_location_entry, container, false)

        //update UI and get references
        val zipCodeEditText: EditText = view.findViewById(R.id.zipcodeEditText)
        zipCodeEditText.text.insert(0, List(5) { Random.nextInt(0,9) }.joinToString(""))


        val enterButton: Button = view.findViewById(R.id.enterButton)
        enterButton.setOnClickListener {
            val zipCode = zipCodeEditText.text.toString()
            if(zipCode.length != 5)
                Toast.makeText(requireContext(), R.string.zipcode_entry_error, Toast.LENGTH_SHORT).show()
            else {
                locationRepository.saveLocation(Location.ZipCode(zipCode))
                // basically shows the forecast list fragment or whatever fragment is preceding this current one
                findNavController().navigateUp()
            }
//                appNavigator.navigateToCurrentForecast(zipCode)
        }

        return view
    }
}