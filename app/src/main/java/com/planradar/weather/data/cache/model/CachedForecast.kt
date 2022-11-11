package com.planradar.weather.data.cache.model
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.planradar.weather.domain.model.Forecast

@Entity(tableName = "forecast")
data class CachedForecast(
    @PrimaryKey(autoGenerate = true) var id: Long = 0L,
    var cityId: Long,
    val date: Long,
    val formattedDate: String,
    val temp: String,
    val feels_like: String,
    val temp_min: String,
    val temp_max: String,
    val pressure: String,
    val sea_level: String,
    val grnd_level: String,
    val humidity: String,
    val temp_kf: String,
    val weather: String,
    val icon: String,
    val cloud: String,
    val windSpeed: String,
    val visibility: Int,
) {
    companion object {
        fun fromDomain(cityId: Long, forecast: Forecast): CachedForecast {
            return CachedForecast(
                cityId = cityId,
                date = forecast.date,
                formattedDate = forecast.formattedDate,
                temp = forecast.temp,
                feels_like = forecast.feels_like,
                temp_min = forecast.temp_min,
                temp_max = forecast.temp_max,
                pressure = forecast.pressure,
                sea_level = forecast.sea_level,
                grnd_level = forecast.grnd_level,
                humidity = forecast.humidity,
                temp_kf = forecast.temp_kf,
                weather = forecast.weather,
                icon = forecast.icon,
                cloud = forecast.cloud,
                windSpeed = forecast.windSpeed,
                visibility = forecast.visibility,
            )
        }
    }
}

fun CachedForecast.toDomain(): Forecast {

    return Forecast(
        date = date,
        formattedDate = formattedDate,
        temp = temp,
        feels_like = feels_like,
        temp_min = temp_min,
        temp_max = temp_max,
        pressure = pressure,
        sea_level = sea_level,
        grnd_level = grnd_level,
        humidity = humidity,
        temp_kf = temp_kf,
        weather = weather,
        icon = icon,
        cloud = cloud,
        windSpeed = windSpeed,
        visibility = visibility,
    )
}