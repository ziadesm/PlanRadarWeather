package com.planradar.weather.domain.model

import java.io.Serializable

data class City(
    val id: Long,
    val name: String,
    val country: String? = null,
    val population: Long? = null,
    val lat: Double? = null,
    val lng: Double? = null,
    val sunrise: Long? = null,
    val sunset: Long? = null,
    var weather: List<Forecast>? = null
): Serializable

data class Forecast(
    val date: Long,
    val formattedDate: String,
    val temp: String,
    val feels_like: String,
    val temp_min: String,
    val temp_max: String,
    val pressure: String,
    val sea_level: String,
    val grnd_level: String,
    val humidity: String,
    val temp_kf: String,
    val weather: String,
    val icon: String,
    val cloud: String,
    val windSpeed: String,
    val visibility: Int,
): Serializable
