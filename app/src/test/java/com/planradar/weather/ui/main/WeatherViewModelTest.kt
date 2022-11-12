package com.planradar.weather.ui.main
import com.planradar.weather.data.FakeRepository
import com.planradar.weather.data.api.model.WeatherCityResponse
import com.planradar.weather.domain.repository.WeatherRepository
import com.planradar.weather.domain.usecases.*
import com.planradar.weather.presentation.MainEventHolder
import com.planradar.weather.presentation.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class WeatherViewModelTest {
    private lateinit var repository: WeatherRepository
    lateinit var viewModel: MainViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        repository = FakeRepository()

        viewModel = MainViewModel(
            getLastWeather = GetLastWeather(repository),
            getCityHistory = GetCityHistory(repository),
            getAllCity = GetAllCity(repository),
            getAllCityHistory = GetAllCityHistory(repository),
            fetchWeather = FetchWeather(repository),
            insertCity = InsertCity(repository),
            UnconfinedTestDispatcher()
        )
    }

    @Test
    fun testGetForecastByCity_cityExists_returnsForecast() = runBlocking {
        // Given
        repository.requestWeather("Cairo")

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
        /*viewModel.setState(MainEventHolder.GettingCityHistory())
        val res = viewModel.forecasts.first { it != null }!!*/
        // Then
//        assertEquals(forecast.temp, res.first().temp)
    }


}