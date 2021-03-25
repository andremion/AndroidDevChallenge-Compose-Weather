package com.example.androiddevchallenge.ui.cities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.data.WeatherRepository
import com.example.androiddevchallenge.domain.model.City
import com.example.androiddevchallenge.ui.city.CityDetailsViewModel
import com.example.androiddevchallenge.util.logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private val Log = logger(CityDetailsViewModel::class.java)

class MyCitiesViewModel(
    repository: WeatherRepository = WeatherRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(MyCitiesViewState())
    val state: StateFlow<MyCitiesViewState> = _state

    init {
        repository.getMyCities()
            .onEach { _state.value = MyCitiesViewState(it) }
            .catch { e -> Log.error(e.message.orEmpty(), e) }
            .launchIn(viewModelScope)
    }
}

data class MyCitiesViewState(
    val cities: List<City> = emptyList()
)
