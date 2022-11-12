package com.planradar.weather.domain.usecases
import com.planradar.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetLastWeather
@Inject
constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(name: String) = try {
        weatherRepository.getLastWeather(name)
    } catch (e: Exception) {
        throw e
    }
}