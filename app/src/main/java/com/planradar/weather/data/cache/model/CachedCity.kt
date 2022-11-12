package com.planradar.weather.data.cache.model
import androidx.room.ColumnInfo
import androidx.room.ColumnInfo.NOCASE
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.planradar.weather.domain.model.City

@Entity(tableName = "city")
data class CachedCity(
    @PrimaryKey(autoGenerate = false)
    var cityId: Long = 0L,
    @ColumnInfo(collate = NOCASE) var name: String,
    val main_humidity: Int? = null,
    val main_temp: Double? = null,
    val wind_speed: Double? = null,
    val weather_icon: String? = null,
    val weather_main: String? = null,
    val date: Long? = null,
) {
    companion object {
        fun fromDomain(city: City): CachedCity {
            return CachedCity(
                cityId = city.id,
                name = city.name,
                main_humidity = city.main_humidity,
                main_temp = city.main_temp,
                wind_speed = city.wind_speed,
                weather_icon = city.weather_icon,
                weather_main = city.weather_main,
                date = city.date
            )
        }
        fun toDomain(city: CachedCity): City {
            return City(
                id = city.cityId,
                name = city.name,
                main_humidity = city.main_humidity,
                main_temp = city.main_temp,
                wind_speed = city.wind_speed,
                weather_icon = city.weather_icon,
                weather_main = city.weather_main,
                date = city.date
            )
        }
    }
}
