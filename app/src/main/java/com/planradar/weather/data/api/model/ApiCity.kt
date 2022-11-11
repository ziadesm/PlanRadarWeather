package com.planradar.weather.data.api.model


data class ApiCity(
    val id: Long?,
    val name: String?,
    val country: String?,
    val population: Long?,
    val timezone: Int?,
    val sunrise: Long?,
    val sunset: Long?,
    val coord: CityCoord?,
)

data class CityCoord(
    val lat: Double?,
    val lon: Double?,
)
