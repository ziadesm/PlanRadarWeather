package com.planradar.weather.presentation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.planradar.weather.di.IoDispatcher
import com.planradar.weather.domain.model.City
import com.planradar.weather.domain.usecases.FetchWeather
import com.planradar.weather.domain.usecases.GetWeather
import com.planradar.weather.presentation.model.ForecastUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val getWeather: GetWeather,
    private val fetchWeather: FetchWeather,
    @IoDispatcher private val io: CoroutineDispatcher
) : ViewModel() {

    private val _forecasts = MutableStateFlow<List<ForecastUI>?>(null)
    val forecasts: StateFlow<List<ForecastUI>?> = _forecasts.asStateFlow()

    private val _allCities = MutableStateFlow<List<City>?>(null)
    val allCities: StateFlow<List<City>?> = _allCities.asStateFlow()

    private val _error = MutableStateFlow(Pair(false, ""))
    val error: StateFlow<Pair<Boolean, String>> = _error.asStateFlow()

    init {
        viewModelScope.launch {
            _allCities.emit(
                arrayListOf(
                    City(0L, "London"),
                    City(1L, "Paris"),
                    City(2L, "Vienna")
                )
            )
        }
    }

    fun getWeatherById(city: String) {
        viewModelScope.launch(io) {
            _forecasts.emit(null)
            try {
                getFromCache(city)
                fetchWeather.invoke(city)
            } catch (e: Throwable) {
                getFromCache(city)
            } catch (e: Exception) {
                _error.emit(Pair(true, "Error fetching data"))
                e.printStackTrace()
            }
        }
    }

    private suspend fun getFromCache(city: String) {
        try {
            getWeather(city).map { forecast ->
                forecast.weather?.map { ForecastUI.fromDomain(it) }
            }.collect { onForecastReceived(it ?: arrayListOf()) }
        } catch (e: Exception) {
            _error.emit(Pair(true, "Error fetching data"))
        }
    }


    private suspend fun onForecastReceived(uiList: List<ForecastUI>) {
        _forecasts.emit(uiList)
    }
}