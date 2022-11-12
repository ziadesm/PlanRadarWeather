package com.planradar.weather.data.cache.model.relations
import androidx.room.Embedded
import androidx.room.Relation
import com.planradar.weather.data.cache.model.CachedHistoryCity
import com.planradar.weather.data.cache.model.CachedHistoryForecast

data class CachedHistoryWithForecast(
    @Embedded val cachedCity: CachedHistoryCity,
    @Relation(parentColumn = "cityId", entityColumn = "cityId")
    val cachedForecast: List<CachedHistoryForecast>
)
