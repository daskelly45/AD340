package com.dave45.net.ad340.location

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.dave45.net.ad340.AppNavigator
import com.dave45.net.ad340.R
import kotlin.random.Random

/**
 * A simple [Fragment] subclass.
 * Use the [LocationEntryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LocationEntryFragment : Fragment() {

    private lateinit var appNavigator: AppNavigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appNavigator  = context as AppNavigator
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            else
                appNavigator.navigateToCurrentForecast(zipCode)
        }

        return view
    }
}