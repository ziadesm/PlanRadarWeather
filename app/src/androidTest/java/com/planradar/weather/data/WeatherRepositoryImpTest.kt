package com.planradar.weather.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.platform.app.InstrumentationRegistry
import com.planradar.weather.data.api.ApiConstants
import com.planradar.weather.data.api.WeatherApi
import com.planradar.weather.data.api.utils.FakeServer
import com.planradar.weather.data.cache.WeatherDatabase
import com.planradar.weather.data.cache.daos.WeatherDao
import com.planradar.weather.domain.repository.WeatherRepository
import com.planradar.weather.domain.repository.WeatherRepositoryImpl
import com.planradar.weather.network.NetworkResponse
import com.planradar.weather.utils.GeneralConstants
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.After
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


@HiltAndroidTest
class WeatherRepositoryImpTest {

    private val fakeServer = FakeServer()
    private lateinit var repository: WeatherRepository

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    lateinit var cache: WeatherDao

    @Inject
    lateinit var database: WeatherDatabase

    @Inject
    lateinit var api: WeatherApi

    @Module
    @InstallIn(ViewModelComponent::class)
    object ViewModelModule {
        @Provides
        fun provideWeather(cache: WeatherDao, api: WeatherApi): WeatherRepository {
            return FakeRepository()
        }
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object TestCacheModule {
        @Singleton
        @Provides
        fun provideWeatherDatabase(@ApplicationContext context: Context) =
            Room.databaseBuilder(context, WeatherDatabase::class.java, GeneralConstants.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .setJournalMode(RoomDatabase.JournalMode.AUTOMATIC)
                .build()

        @Singleton
        @Provides
        fun provideWeatherDao(db: WeatherDatabase): WeatherDao = db.weatherDao()

        @Provides
        @Singleton
        fun provideWeatherApi(): WeatherApi {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    val newRequest = chain.request().newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json")
                        .build()
                    chain.proceed(newRequest)
                }
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(ApiConstants.BASE_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApi::class.java)
        }

    }

    @Before
    fun setup() {
        fakeServer.start()

        hiltRule.inject()

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
        resultCity.collect{
            when(it) {
                is NetworkResponse.Success -> assert(expectedCityId == it.body.id)
                else -> Log.e("TestingFail", "Test requestForecast_returnsCorrectCity fail")
            }
        }
    }

    @Test
    fun requestForecast_returnsCorrectWeather() = runBlocking {
        // Given
        val expectedTemp = 301.0
        fakeServer.setHappyPathDispatcher()
        // When
        val resultCity = repository.requestWeather("Cairo")
        // Then
        resultCity.collect{
            when(it) {
                is NetworkResponse.Success -> assert(expectedTemp == it.body.main_temp)
                else -> Log.e("TestingFail", "Test requestForecast_returnsCorrectWeather fail")
            }
        }
    }

    @Test
    fun storeWeather() {
        // Given
        val expectedCityId = 360630L
        runBlocking {
            fakeServer.setHappyPathDispatcher()
            val resultCity = repository.requestWeather("Cairo")
            // When
            resultCity.collect {
                when(it) {
                    is NetworkResponse.Success -> {
                        repository.storeWeather(it.body, Calendar.getInstance().timeInMillis)
                        // Then
                        val insertedValue = repository.getLastWeather("Cairo").first()
                        assert(insertedValue.cityId == expectedCityId)
                    }
                    else -> Log.e("TestingFail", "Test storeWeather fail")
                }
            }

        }
    }

    @After
    fun teardown() {
        fakeServer.shutdown()
    }
}