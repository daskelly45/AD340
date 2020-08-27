package com.dave45.net.ad340

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

sealed class Location {
    data class ZipCode(val zipCode: String): Location()
}

private const val KEY_ZIP_CODE = "key_zipcode"

class LocationRepository(context: Context) {
    private val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    private val _savedLocation = MutableLiveData<Location>()

    val savedLocation: LiveData<Location> = _savedLocation

    init {
        preferences.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key != KEY_ZIP_CODE) return@registerOnSharedPreferenceChangeListener

            broadCastSavedZipCode()
        }

        broadCastSavedZipCode()
    }

    fun saveLocation(location: Location) {
        when(location) {
            is Location.ZipCode -> preferences.edit().putString(KEY_ZIP_CODE, location.zipCode).commit()
        }
    }

    private fun broadCastSavedZipCode() {
        val zipCode = preferences.getString(KEY_ZIP_CODE, "")

        if (zipCode != null && zipCode.isNotBlank()) {
            _savedLocation.value = Location.ZipCode(zipCode)
        }
    }
}