package com.example.androiddevchallenge.ui.city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.data.WeatherRepository
import com.example.androiddevchallenge.domain.model.CityDetails
import com.example.androiddevchallenge.util.logger
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private val Log = logger(CityDetailsViewModel::class.java)

@OptIn(FlowPreview::class)
class CityDetailsViewModel(
    private val repository: WeatherRepository = WeatherRepository()
) : ViewModel() {

    private val selectedCityId = MutableStateFlow("")

    private val _state = MutableStateFlow(CityDetailViewState())
    val state: StateFlow<CityDetailViewState> = _state

    init {
        selectedCityId
            .filter { it.isNotBlank() }
            .flatMapConcat {
                repository.getCityDetails(it)
                    .catch { e -> Log.error(e.message.orEmpty(), e) }
            }
            .onEach { _state.value = CityDetailViewState(it) }
            .launchIn(viewModelScope)
    }

    fun onCitySelected(id: String) {
        selectedCityId.value = id
    }
}

data class CityDetailViewState(
    val cityDetails: CityDetails? = null
)
