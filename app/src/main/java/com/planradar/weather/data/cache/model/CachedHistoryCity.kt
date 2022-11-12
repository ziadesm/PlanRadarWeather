package com.planradar.weather.data.cache.model
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.planradar.weather.domain.model.City

@Entity(tableName = "city_history", indices = [Index(value = ["date"])])
data class CachedHistoryCity(
    var cityId: Long = 0L,
    @ColumnInfo(collate = ColumnInfo.NOCASE)
    var name: String,
    @PrimaryKey(autoGenerate = false)
    var date: Long,
    val main_humidity: Int? = null,
    val main_temp: Double? = null,
    val wind_speed: Double? = null,
    val weather_icon: String? = null,
    val weather_main: String? = null,
) {
    companion object {
        fun fromDomain(city: City, date: Long): CachedHistoryCity {
            return CachedHistoryCity(
                cityId = city.id,
                name = city.name,
                date = date,
                main_humidity = city.main_humidity,
                main_temp = city.main_temp,
                wind_speed = city.wind_speed,
                weather_icon = city.weather_icon,
                weather_main = city.weather_main
            )
        }
        fun toDomain(city: CachedHistoryCity): City {
            return City(
                id = city.cityId,
                name = city.name,
                date = city.date,
                main_humidity = city.main_humidity,
                main_temp = city.main_temp,
                wind_speed = city.wind_speed,
                weather_icon = city.weather_icon,
                weather_main = city.weather_main
            )
        }
    }
}
