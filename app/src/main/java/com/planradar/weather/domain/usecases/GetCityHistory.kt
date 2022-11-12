package com.planradar.weather.domain.usecases

import com.planradar.weather.domain.model.City
import com.planradar.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetCityHistory
@Inject
constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(city: String, date: Long) = try {
        weatherRepository.getCityHistory(city, date)
    } catch (e: Exception) {
        throw e
    }
}