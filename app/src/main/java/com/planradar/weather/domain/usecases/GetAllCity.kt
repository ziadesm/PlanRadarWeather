package com.planradar.weather.domain.usecases

import com.planradar.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetAllCity
@Inject
constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke() = try {
        weatherRepository.getAllCity()
    } catch (e: Exception) {
        throw e
    }
}