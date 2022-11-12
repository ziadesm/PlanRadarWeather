package com.planradar.weather.data.api.model

import java.io.Serializable

data class WeatherCityResponse(
    val id: Long,
    val main: Main,
    val name: String,
    val weather: List<AllWeatherModel>,
    val wind: Wind,
    val message: String? = null,
    val cod: String
): Serializable {

    data class Main(
        val humidity: Int?,
        val temp: Double?,
    ): Serializable

    data class AllWeatherModel(
        val icon: String?,
        val main: String?
    ): Serializable

    data class Wind(
        val speed: Double?
    ): Serializable
}
