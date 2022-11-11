package com.planradar.weather.di
import com.planradar.weather.data.api.WeatherApi
import com.planradar.weather.data.cache.daos.WeatherDao
import com.planradar.weather.domain.repository.WeatherRepository
import com.planradar.weather.domain.repository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @Provides
    fun provideWeatherRepo(cache: WeatherDao, api: WeatherApi): WeatherRepository {
        return WeatherRepositoryImpl(cache, api)
    }
}