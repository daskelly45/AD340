package com.dave45.net.ad340.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dave45.net.ad340.DATE_FORMAT
import com.dave45.net.ad340.formatIconIdToOpenWeatherIconUri
import java.util.*

class ForecastDetailsViewModel: ViewModel() {
    private val _viewState = MutableLiveData<ForecastDetailsViewState>()
    val viewState: LiveData<ForecastDetailsViewState> = _viewState

    fun processArgs(args: ForecastDetailsFragmentArgs) {
        if (viewState.value != null) return

        _viewState.value = ForecastDetailsViewState(
            temp = args.temp,
            description = args.description,
            date = DATE_FORMAT.format(Date(args.date * 1000)),
            iconUrl = formatIconIdToOpenWeatherIconUri(args.iconId)
        )
    }
}