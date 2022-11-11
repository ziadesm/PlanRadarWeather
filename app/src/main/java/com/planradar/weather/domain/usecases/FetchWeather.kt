package com.planradar.weather.domain.usecases
import com.planradar.weather.data.api.model.mapToDomain
import com.planradar.weather.domain.repository.WeatherRepository
import com.planradar.weather.network.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchWeather @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(city: String) {
        return withContext(Dispatchers.IO) {
            try {
                val weather = weatherRepository.requestWeather(city)
                weather.distinctUntilChanged().map {
                    when(it) {
                        is NetworkResponse.Success -> weatherRepository.storeWeather(it.body.mapToDomain())
                        else -> {}
                    }
                }
            } catch (e: Exception) { throw e }
        }
    }
}