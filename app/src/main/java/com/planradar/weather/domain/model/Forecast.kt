package com.planradar.weather.domain.model

import java.io.Serializable

data class City(
    val id: Long,
    val name: String,
    val main_humidity: Int? = null,
    val main_temp: Double? = null,
    val wind_speed: Double? = null,
    val weather_icon: String? = null,
    val weather_main: String? = null,
    val date: Long? = null,
): Serializable