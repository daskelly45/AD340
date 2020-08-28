package com.dave45.net.ad340.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dave45.net.ad340.DATE_FORMAT
import com.dave45.net.ad340.formatIconIdToOpenWeatherIconUri
import java.util.*


class ForecastDetailsViewModelFactory(private val args: ForecastDetailsFragmentArgs): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ForecastDetailsViewModel::class.java))
            return ForecastDetailsViewModel(args) as T

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


class ForecastDetailsViewModel(args: ForecastDetailsFragmentArgs): ViewModel() {
    private val _viewState = MutableLiveData<ForecastDetailsViewState>()
    val viewState: LiveData<ForecastDetailsViewState> = _viewState

    init {
        _viewState.value = ForecastDetailsViewState(
            temp = args.temp,
            description = args.description,
            date = DATE_FORMAT.format(Date(args.date * 1000)),
            iconUrl = formatIconIdToOpenWeatherIconUri(args.iconId)
        )
    }
}