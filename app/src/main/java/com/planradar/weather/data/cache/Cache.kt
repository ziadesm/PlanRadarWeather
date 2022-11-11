package com.planradar.weather.data.cache
import com.planradar.weather.data.cache.model.CachedCity
import com.planradar.weather.data.cache.model.CachedCityWithForecast
import com.planradar.weather.data.cache.model.CachedForecast
import com.planradar.weather.domain.model.City
import kotlinx.coroutines.flow.Flow

interface Cache {
    fun getForecast(name: String): Flow<CachedCityWithForecast>

    suspend fun storeCity(city: CachedCity)

    suspend fun storeForecast(vararg forecast: CachedForecast)
}