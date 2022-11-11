package com.planradar.weather.data.api
import com.planradar.weather.BuildConfig
import com.planradar.weather.data.api.model.ApiContainer
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET(ApiConstants.CITY_CAST)
    suspend fun getForecast(
        @Query(ApiParameters.API_KEY) apiKey: String = BuildConfig.API_KEY,//API_KEY,
        @Query(ApiParameters.CITY_QUERY) city: String,
    ): ApiContainer

    /*@GET(ApiConstants.FORECAST_ENDPOINT)
    suspend fun getWeatherImage(
        @Query(ApiParameters.API_KEY) apiKey: String = API_KEY,
        @Query(ApiParameters.CITY_QUERY) city: String,
    ): Nothing*/

}