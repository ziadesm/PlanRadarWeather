package com.planradar.weather.data.api.model
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.planradar.weather.data.utils.JsonReader
import com.planradar.weather.data.mapper.NetworkMapper
import com.planradar.weather.domain.model.City
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ApiWeatherTest {
    lateinit var apiResponse: WeatherCityResponse

    private val mMapper by lazy { NetworkMapper() }
    @Before
    fun gettingReady() {
        val gson = GsonBuilder().serializeNulls().create();
        val type = object : TypeToken<WeatherCityResponse>() {}.type

        val json = JsonReader.getJson("forecast.json")
        apiResponse = gson.fromJson(json, type)!!
    }


    @Test
    fun testMapCityFromApiToDomain() {
        // GIVEN
        val expectedValue = City(
            360630,
            "Cairo",
            main_humidity = 56,
            main_temp = 296.16,
            wind_speed = 6.1,
            weather_icon = "03n",
            weather_main = "Clouds"
        )
        // WHEN
        val result = mMapper.mapToEntity(expectedValue)
        // THEN
        assertEquals(expectedValue.id, result.id)
        assertEquals(expectedValue.name, result.name)
    }


}