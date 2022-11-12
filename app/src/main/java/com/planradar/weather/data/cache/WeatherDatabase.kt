package com.planradar.weather.data.cache
import androidx.room.Database
import androidx.room.RoomDatabase
import com.planradar.weather.data.cache.daos.WeatherDao
import com.planradar.weather.data.cache.model.CachedCity
import com.planradar.weather.data.cache.model.CachedForecast
import com.planradar.weather.data.cache.model.CachedHistoryCity
import com.planradar.weather.data.cache.model.CachedHistoryForecast

@Database(
    entities = [
        CachedCity::class,// CachedForecast::class,
        CachedHistoryCity::class,// CachedHistoryForecast::class
    ],
    version = 98
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}