package com.planradar.weather.data.cache
import androidx.room.Database
import androidx.room.RoomDatabase
import com.planradar.weather.data.cache.daos.WeatherDao
import com.planradar.weather.data.cache.model.CachedCity
import com.planradar.weather.data.cache.model.CachedForecast

@Database(
    entities = [
        CachedCity::class, CachedForecast::class
    ],
    version = 1
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}