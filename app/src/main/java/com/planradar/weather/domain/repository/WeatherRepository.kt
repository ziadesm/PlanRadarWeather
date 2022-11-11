package com.planradar.weather.domain.repository
import com.planradar.weather.data.api.model.ApiContainer
import com.planradar.weather.domain.model.City
import com.planradar.weather.network.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun requestWeather(city: String): Flow<NetworkResponse<ApiContainer, Throwable>>
    suspend fun storeWeather(city: City)
    fun getWeather(name: String): Flow<City>
}