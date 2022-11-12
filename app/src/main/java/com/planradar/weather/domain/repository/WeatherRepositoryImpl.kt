package com.planradar.weather.domain.repository
import com.planradar.weather.data.api.WeatherApi
import com.planradar.weather.data.cache.daos.WeatherDao
import com.planradar.weather.data.cache.model.CachedCity
import com.planradar.weather.data.cache.model.CachedForecast
import com.planradar.weather.data.cache.model.CachedHistoryCity
import com.planradar.weather.data.cache.model.CachedHistoryForecast
import com.planradar.weather.data.cache.model.relations.CachedCityWithHistory
import com.planradar.weather.data.cache.model.relations.CachedHistoryWithForecast
import com.planradar.weather.data.cache.model.relations.toDomain
import com.planradar.weather.data.mapper.NetworkMapper
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
    private val mNetworkMapper by lazy { NetworkMapper() }
    override suspend fun requestWeather(city: String) = flow {
        try {
            val results = api.getForecast(city = city)
            if (results.cod.toInt() in 200..399) emit(NetworkResponse.Success(mNetworkMapper.mapFromEntity(results)))
            else emit(NetworkResponse.UnknownError(Throwable(results.message)))
        } catch (exception: Throwable) {
            emit(NetworkResponse.UnknownError(exception))
        }
    }

    override suspend fun storeWeather(city: City, date: Long) {
        cache.insertCity(CachedCity.fromDomain(city))
        cache.insertCityHistory(CachedHistoryCity.fromDomain(city, date))
    }

    override fun getLastWeather(name: String) = cache.getSingleCity(name)

    override fun getAllCityHistory(name: String): Flow<List<CachedHistoryCity>> = cache.getAllCityHistory(name)
    override fun getCityHistory(name: String, date: Long) = cache.getCityHistory(name, date)
    override fun getAllCity() = cache.getAllCities()

}