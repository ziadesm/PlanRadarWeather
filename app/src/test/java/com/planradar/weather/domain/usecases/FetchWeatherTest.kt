package com.planradar.weather.domain.usecases
import com.planradar.weather.data.FakeRepository
import com.planradar.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class UseCasesTest {

    private lateinit var repository: WeatherRepository

    private lateinit var getWeatherUseCase: GetLastWeather
    private lateinit var fetchWeatherUseCase: FetchWeather


    @Before
    fun setUp() {
        repository = FakeRepository()
        fetchWeatherUseCase = FetchWeather(repository)
        this.getWeatherUseCase = GetLastWeather(repository)
    }


    @Test
    fun testGetForecastByCity() = runBlocking {
        // Given

        val format = "yyyy-MM-dd"
        val formatter = SimpleDateFormat(format)
        val time = formatter.format(Date().time)
        /*val forecast = WeatherCityResponse(
            1666882800,
            "$time 15:00:00",
            "301",
            "301",
            "301",
            "301",
            "1016",
            "1016",
            "1013",
            "42",
            "-.17",
            "broken clouds",
            "04d",
            "52",
            "4.95",
            10000
        )*/

        // When
        try {
            fetchWeatherUseCase("Cairo")
        }catch (e:Exception){
            e.printStackTrace()
        }
        val result = getWeatherUseCase("Cairo")

        // Then
//        assertEquals(forecast.temp, result.first().weather.first().temp)
    }

}