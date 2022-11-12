package com.planradar.weather.data
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.planradar.weather.data.api.model.WeatherCityResponse
import com.planradar.weather.data.cache.model.CachedCity
import com.planradar.weather.data.cache.model.CachedForecast
import com.planradar.weather.data.cache.model.CachedHistoryCity
import com.planradar.weather.data.cache.model.relations.CachedCityWithForecast
import com.planradar.weather.data.cache.model.relations.CachedCityWithHistory
import com.planradar.weather.data.cache.model.relations.toDomain
import com.planradar.weather.data.mapper.NetworkMapper
import com.planradar.weather.data.utils.JsonReader
import com.planradar.weather.domain.model.City
import com.planradar.weather.domain.repository.WeatherRepository
import com.planradar.weather.network.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeRepository @Inject constructor() : WeatherRepository {
    var apiResponse: WeatherCityResponse

    private val mMapper by lazy { NetworkMapper() }
    init {
        val gson = GsonBuilder().serializeNulls().create();
        val type = object : TypeToken<WeatherCityResponse>() {}.type
        val res = JsonReader.getJson("forecast.json")
        apiResponse = gson.fromJson(res, type)!!
    }

    private val mutableLocalCity = mutableListOf<CachedCity>()
    private val mutableHistoryLocalCity = mutableListOf<CachedHistoryCity>()
    private val mutableForecast = mutableListOf<CachedForecast>()

    override suspend fun requestWeather(city: String): Flow<NetworkResponse<City, Throwable>> {
        return flow { NetworkResponse.Success(mMapper.mapFromEntity(apiResponse)) }
    }

    override suspend fun storeWeather(city: City, date: Long) {
        mutableLocalCity.add(CachedCity.fromDomain(city))
        mutableHistoryLocalCity.add(CachedHistoryCity.fromDomain(city, date))
    }

    override fun getLastWeather(name: String): Flow<CachedCity> {
        val cachedCity = mutableLocalCity.first { it.name == name }
        val cachedForecast = mutableForecast.filter { cachedCity.cityId == it.cityId }
        val cached = CachedCityWithForecast(cachedCity, cachedForecast)
        return flow {
            emit(CachedCity.fromDomain(cached.toDomain()))
        }
    }

    override fun getAllCityHistory(name: String): Flow<List<CachedHistoryCity>> {
        return flow {
            emit(mutableHistoryLocalCity)
        }
    }

    override fun getCityHistory(name: String, date: Long): Flow<CachedCityWithHistory> {
        return flow {
            emit(
                CachedCityWithHistory(
                    mutableLocalCity.first(),
                    mutableHistoryLocalCity.first()
                )
            )
        }
    }

    override fun getAllCity(): Flow<List<CachedCity>> {
        return flow {
            emit(mutableLocalCity)
        }
    }
}