package com.planradar.weather.domain.usecases
import com.planradar.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class FetchWeather @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(city: String) = weatherRepository.requestWeather(city)
}