package com.planradar.weather.domain.usecases

import com.planradar.weather.domain.model.City
import com.planradar.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class InsertCity
@Inject
constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(city: City, date: Long) = try {
        weatherRepository.storeWeather(city, date)
    } catch (e: Exception) {
        throw e
    }
}