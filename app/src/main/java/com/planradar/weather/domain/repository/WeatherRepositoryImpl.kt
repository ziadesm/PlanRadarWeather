package com.planradar.weather.domain.repository
import com.planradar.weather.data.api.WeatherApi
import com.planradar.weather.data.cache.daos.WeatherDao
import com.planradar.weather.data.cache.model.CachedCity
import com.planradar.weather.data.cache.model.CachedForecast
import com.planradar.weather.data.cache.model.toDomain
import com.planradar.weather.domain.model.City
import com.planradar.weather.network.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherRepositoryImpl
@Inject
constructor(
    private val cache: WeatherDao,
    private val api: WeatherApi
): WeatherRepository {
    override suspend fun requestWeather(city: String) = flow {
        emit(NetworkResponse.Loading)
        try {
            val results = api.getForecast(city = city)
            emit(NetworkResponse.Success(results))
        } catch (exception: Throwable) {
            emit(NetworkResponse.UnknownError(exception))
        }
    }

    override suspend fun storeWeather(city: City) {
        cache.insertCity(CachedCity.fromDomain(city))
        cache.insertForecast(*city.weather?.map { CachedForecast.fromDomain(city.id, it) }
            ?.toTypedArray() ?: emptyArray())
    }

    override fun getWeather(name: String): Flow<City> =
        cache.getForecast(name).distinctUntilChanged().map {
            it.toDomain()
        }
}