package com.planradar.weather.data.cache.daos
import androidx.room.*
import com.planradar.weather.data.cache.model.*
import com.planradar.weather.data.cache.model.relations.CachedCityWithForecast
import com.planradar.weather.data.cache.model.relations.CachedCityWithHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCityHistory(history: CachedHistoryCity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: CachedCity)


    @Transaction
    @Query("SELECT * FROM city")
    fun getAllCities(): Flow<List<CachedCity>>

    @Transaction
    @Query("SELECT * FROM city WHERE name = :name")
    fun getSingleCity(name: String): Flow<CachedCity>

    @Transaction
    @Query("SELECT * FROM city_history WHERE name = :name")
    fun getAllCityHistory(name: String): Flow<List<CachedHistoryCity>>

    @Transaction
    @Query("SELECT * FROM city_history WHERE name = :name AND date = :date")
    fun getCityHistory(name: String, date: Long): Flow<CachedCityWithHistory>
}