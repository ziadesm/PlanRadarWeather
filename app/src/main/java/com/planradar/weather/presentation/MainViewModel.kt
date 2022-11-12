package com.planradar.weather.presentation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.planradar.weather.data.api.model.WeatherCityResponse
import com.planradar.weather.data.cache.model.CachedCity
import com.planradar.weather.data.cache.model.CachedHistoryCity
import com.planradar.weather.data.cache.model.relations.CachedCityWithHistory
import com.planradar.weather.data.cache.model.relations.toDomain
import com.planradar.weather.di.IoDispatcher
import com.planradar.weather.domain.model.City
import com.planradar.weather.domain.usecases.*
import com.planradar.weather.network.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val getLastWeather: GetLastWeather,
    private val getCityHistory: GetCityHistory,
    private val getAllCity: GetAllCity,
    private val getAllCityHistory: GetAllCityHistory,
    private val fetchWeather: FetchWeather,
    private val insertCity: InsertCity,
    @IoDispatcher private val io: CoroutineDispatcher
) : ViewModel() {

    private val _allCities = MutableStateFlow<List<CachedCity>?>(null)
    val allCities: StateFlow<List<CachedCity>?> = _allCities.asStateFlow()

    private val _cityDetails = MutableStateFlow<City?>(null)
    val cityDetails: StateFlow<City?> = _cityDetails.asStateFlow()

    private val _allCityHistory = MutableStateFlow<List<CachedHistoryCity>?>(null)
    val allCityHistory: StateFlow<List<CachedHistoryCity>?> = _allCityHistory.asStateFlow()

    private val _errorDetails = MutableSharedFlow<Throwable?>()
    val errorDetails: SharedFlow<Throwable?> = _errorDetails.asSharedFlow()

    private val _error = MutableSharedFlow<Throwable?>()
    val error: SharedFlow<Throwable?> = _error.asSharedFlow()

    init {
        viewModelScope.launch {
            _allCities.emit(
                arrayListOf(
                    CachedCity(2643743, "London"),
                    CachedCity(2988507, "Paris"),
                    CachedCity(2761369, "Vienna")
                )
            )
        }
        setState(MainEventHolder.GettingAllCities)
    }

    fun setState(event: MainEventHolder) {
        CoroutineScope(io).launch {
            when(event) {
                is MainEventHolder.GettingWeatherCity -> {
                    if (event.cityDate > 0L) {
                        gettingCityHistoryFromCache(event.cityId, event.cityDate)
                    } else {
                        fetchWeather.invoke(event.cityId).collect {
                            when(it) {
                                is NetworkResponse.Success -> {
                                    insertCity.invoke(it.body, Calendar.getInstance().timeInMillis)
                                    _cityDetails.emit(it.body)
                                    gettingCityFromCache(event.cityId)
                                    setState(MainEventHolder.GettingAllCities)
                                }
                                is NetworkResponse.UnknownError -> {
                                    _errorDetails.emit(it.error)
                                    gettingCityFromCache(event.cityId)
                                }
                            }
                        }
                    }
                }
                is MainEventHolder.GettingCityHistory -> gettingAllCityHistories(event.cityId)
                is MainEventHolder.GettingAllCities -> gettingAllCities()
            }
        }
    }

    private suspend fun gettingCityHistoryFromCache(cityId: String, cityDate: Long) {
        try {
            getCityHistory(cityId, cityDate).collect {
                _cityDetails.emit(it.toDomain())
            }
        } catch (e: Exception) {
            _error.emit(Throwable("Error fetching data"))
        }
    }

    private suspend fun gettingCityFromCache(cityId: String) {
        try {
            getLastWeather(cityId).collect {
                _cityDetails.emit(CachedCity.toDomain(it))
            }
        } catch (e: Exception) {
            _error.emit(Throwable("Error fetching data"))
        }
    }
    private suspend fun gettingAllCities() {
        try {
            getAllCity().collect {
                _allCities.emit(it)
            }
        } catch (e: Exception) {
            _error.emit(Throwable("Error fetching data"))
        }
    }

    private suspend fun gettingAllCityHistories(cityId: String) {
        try {
            getAllCityHistory(cityId).collect {
                _allCityHistory.emit(it)
            }
        } catch (e: Exception) {
            _error.emit(Throwable("Error fetching data"))
        }
    }
}

sealed class MainEventHolder {
    data class GettingWeatherCity(val cityId: String, val cityDate: Long): MainEventHolder()
    data class GettingCityHistory(val cityId: String): MainEventHolder()
    object GettingAllCities: MainEventHolder()
}