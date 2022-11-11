package com.planradar.weather.data.cache.daos
import androidx.room.*
import com.planradar.weather.data.cache.model.CachedCity
import com.planradar.weather.data.cache.model.CachedCityWithForecast
import com.planradar.weather.data.cache.model.CachedForecast
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WeatherDao {
    @Transaction
    @Query("SELECT * FROM city where name = :name")
    abstract fun getForecast(name: String): Flow<CachedCityWithForecast>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertCity(city: CachedCity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertForecast(vararg cachedForecast: CachedForecast)

    @Query("DELETE FROM forecast WHERE cityId = :cityId")
    abstract suspend fun deleteByCity(cityId: Long?)
}