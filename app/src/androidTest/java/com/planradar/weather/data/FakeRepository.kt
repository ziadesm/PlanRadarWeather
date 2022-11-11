package com.planradar.weather.data
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.planradar.weather.data.api.model.ApiContainer
import com.planradar.weather.data.api.model.mapToDomain
import com.planradar.weather.data.cache.model.CachedCity
import com.planradar.weather.data.cache.model.CachedCityWithForecast
import com.planradar.weather.data.cache.model.CachedForecast
import com.planradar.weather.data.cache.model.toDomain
import com.planradar.weather.domain.model.City
import com.planradar.weather.domain.repository.WeatherRepository
import com.planradar.weather.network.NetworkResponse
import com.planradar.weather.utils.JsonReader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeRepository @Inject constructor() : WeatherRepository {
    var apiResponse: ApiContainer

    init {
        val gson = GsonBuilder().serializeNulls().create();
        val type = object : TypeToken<ApiContainer>() {}.type
        val res = JsonReader.getJson("forecast.json")
        apiResponse = gson.fromJson(res, type)!!
    }

    val localCity: List<CachedCity> get() = mutableLocalCity
    private val mutableLocalCity = mutableListOf<CachedCity>()
    val localForecast: List<CachedForecast> get() = mutableForecast
    private val mutableForecast = mutableListOf<CachedForecast>()

    override suspend fun requestWeather(city: String): Flow<NetworkResponse<ApiContainer, Throwable>> {
        return flow { NetworkResponse.Success(apiResponse.mapToDomain()) }
    }

    override suspend fun storeWeather(city: City) {
        mutableLocalCity.add(CachedCity.fromDomain(city))
        city.weather?.forEach {
            mutableForecast.add(CachedForecast.fromDomain(city.id, it))
        }
    }

    override fun getWeather(name: String): Flow<City> {
        val cachedCity = mutableLocalCity.first { it.name == name }
        val cachedForecast = mutableForecast.filter { cachedCity.cityId == it.cityId }
        val cached = CachedCityWithForecast(cachedCity, cachedForecast)
        return flow {
            emit(cached.toDomain())
        }
    }
}