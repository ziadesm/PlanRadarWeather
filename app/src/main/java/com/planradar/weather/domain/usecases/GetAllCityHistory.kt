package com.planradar.weather.domain.usecases

import com.planradar.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetAllCityHistory
@Inject
constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(city: String) = try {
        weatherRepository.getAllCityHistory(city)
    } catch (e: Exception) {
        throw e
    }
}