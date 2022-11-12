package com.planradar.weather.domain.repository
import com.planradar.weather.data.api.model.WeatherCityResponse
import com.planradar.weather.data.cache.model.CachedCity
import com.planradar.weather.data.cache.model.CachedHistoryCity
import com.planradar.weather.data.cache.model.relations.CachedCityWithHistory
import com.planradar.weather.data.cache.model.relations.CachedHistoryWithForecast
import com.planradar.weather.domain.model.City
import com.planradar.weather.network.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun requestWeather(city: String): Flow<NetworkResponse<City, Throwable>>
    suspend fun storeWeather(city: City, date: Long)
    fun getLastWeather(name: String): Flow<CachedCity>
    fun getAllCityHistory(name: String): Flow<List<CachedHistoryCity>>
    fun getCityHistory(name: String, date: Long): Flow<CachedCityWithHistory>
    fun getAllCity(): Flow<List<CachedCity>>
}