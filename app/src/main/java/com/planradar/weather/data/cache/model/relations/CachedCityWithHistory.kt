package com.planradar.weather.data.cache.model.relations
import androidx.room.Embedded
import androidx.room.Relation
import com.planradar.weather.data.cache.model.CachedCity
import com.planradar.weather.data.cache.model.CachedHistoryCity
import com.planradar.weather.domain.model.City

data class CachedCityWithHistory(
    @Embedded val cachedCity: CachedCity,
    @Relation(parentColumn = "date", entityColumn = "date")
    val cachedForecast: CachedHistoryCity
)

fun CachedCityWithHistory.toDomain(): City {
    return City(
        id = cachedCity.cityId,
        name = cachedCity.name,
        main_humidity = cachedForecast.main_humidity,
        main_temp = cachedForecast.main_temp,
        wind_speed = cachedForecast.wind_speed,
        weather_main = cachedForecast.weather_main,
        weather_icon = cachedForecast.weather_icon,
        date = cachedCity.date
    )
}
