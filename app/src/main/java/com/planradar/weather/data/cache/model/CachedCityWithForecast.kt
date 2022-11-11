package com.planradar.weather.data.cache.model
import androidx.room.Embedded
import androidx.room.Relation
import com.planradar.weather.domain.model.City

data class CachedCityWithForecast(
    @Embedded val cachedCity: CachedCity,
    @Relation(parentColumn = "cityId", entityColumn = "cityId")
    val cachedForecast: List<CachedForecast>
)

fun CachedCityWithForecast.toDomain(): City {

    return City(id = cachedCity.cityId,
        name = cachedCity.name,
        country = cachedCity.country,
        population = cachedCity.population,
        lat = cachedCity.lat,
        lng = cachedCity.lng,
        sunrise = cachedCity.sunrise,
        sunset = cachedCity.sunset,
        weather = cachedForecast.map {
            it.toDomain()
        })
}