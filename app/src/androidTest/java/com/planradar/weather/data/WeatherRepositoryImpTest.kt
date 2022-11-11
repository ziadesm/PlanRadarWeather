package com.planradar.weather.data

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.planradar.weather.data.api.WeatherApi
import com.planradar.weather.data.api.utils.FakeServer
import com.planradar.weather.data.cache.WeatherDatabase
import com.planradar.weather.data.cache.daos.WeatherDao
import com.planradar.weather.domain.repository.WeatherRepository
import com.planradar.weather.domain.repository.WeatherRepositoryImpl
import com.planradar.weather.network.NetworkResponse
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton


@HiltAndroidTest
class WeatherRepositoryImpTest {

    private val fakeServer = FakeServer()
    private lateinit var repository: WeatherRepository
    private lateinit var api: WeatherApi

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    lateinit var cache: WeatherDao

    @Inject
    lateinit var database: WeatherDatabase

    @Inject
    lateinit var retrofitBuilder: Retrofit.Builder

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class TestCacheModule {
        @Binds
        abstract fun bindCache(cache: WeatherDatabase): WeatherDao

        companion object {

            @Provides
            @Singleton
            fun provideRoomDatabase(): WeatherDatabase {
                return Room.inMemoryDatabaseBuilder(
                    InstrumentationRegistry.getInstrumentation().context,
                    WeatherDatabase::class.java
                )
                    .allowMainThreadQueries()
                    .build()
            }

            @Provides
            fun weatherDao(
                weatherDatabase: WeatherDatabase
            ): WeatherDao = weatherDatabase.weatherDao()
        }

    }

    @Before
    fun setup() {
        fakeServer.start()

        hiltRule.inject()

        api = retrofitBuilder
            .baseUrl(fakeServer.baseEndpoint)
            .build()
            .create(WeatherApi::class.java)

        cache = database.weatherDao()

        repository = WeatherRepositoryImpl(
            cache,
            api
        )
    }

    @Test
    fun requestForecast_returnsCorrectCity() = runBlocking {
        // Given
        val expectedCityId = 360630L
        fakeServer.setHappyPathDispatcher()
        // When
        val resultCity = repository.requestWeather("Cairo")
        // Then
//        assert(expectedCityId == resultCity.first())
    }

    @Test
    fun requestForecast_returnsCorrectWeather() = runBlocking {
        // Given
        val expectedTemp = "301"
        fakeServer.setHappyPathDispatcher()
        // When
        val resultCity = repository.requestWeather("Cairo")
        // Then
//        assert(expectedTemp == resultCity.weather.first().temp)
    }

    @Test
    fun storeWeather() {
        // Given
        val expectedCityId = 360630L
        runBlocking {
            fakeServer.setHappyPathDispatcher()
            // When
            /*when(val resultCity = repository.requestWeather("Cairo")) {
                is NetworkResponse.Success -> repository.storeWeather(resultCity.body)
            }*/

            // Then
            val insertedValue = repository.getWeather("Cairo").first()
            assert(insertedValue.id == expectedCityId)
        }
    }

    @After
    fun teardown() {
        fakeServer.shutdown()
    }
}