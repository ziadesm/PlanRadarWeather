package com.planradar.weather.data.cache.model.relations
import androidx.room.Embedded
import androidx.room.Relation
import com.planradar.weather.data.cache.model.CachedCity
import com.planradar.weather.data.cache.model.CachedForecast
import com.planradar.weather.domain.model.City

data class CachedCityWithForecast(
    @Embedded val cachedCity: CachedCity,
    @Relation(parentColumn = "cityId", entityColumn = "cityId")
    val cachedForecast: List<CachedForecast>
)

fun CachedCityWithForecast.toDomain(): City {

    return City(
        id = cachedCity.cityId,
        name = cachedCity.name,
        main_humidity = cachedCity.main_humidity,
        main_temp = cachedCity.main_temp,
        wind_speed = cachedCity.wind_speed,
        weather_icon = cachedCity.weather_icon,
        weather_main = cachedCity.weather_main,
        date = cachedCity.date
    )
}