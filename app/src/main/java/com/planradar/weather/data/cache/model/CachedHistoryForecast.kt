package com.planradar.weather.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_forecast")
data class CachedHistoryForecast(
    var cityId: Long,
    var id: Long = 0L,
    @PrimaryKey(autoGenerate = false)
    val date: Long,
    val formattedDate: String,
    val temp: String,
    val feels_like: String,
    val humidity: String,
    val weather: String,
    val icon: String,
    val windSpeed: String,
)
