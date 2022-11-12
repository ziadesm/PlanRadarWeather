package com.planradar.weather.data.mapper

import com.planradar.weather.data.api.model.WeatherCityResponse
import com.planradar.weather.domain.model.City

class NetworkMapper: EntityMapper<WeatherCityResponse, City> {
    override fun mapFromEntity(entity: WeatherCityResponse): City {
        return City(
            id = entity.id,
            name = entity.name,
            main_humidity = entity.main.humidity,
            main_temp = entity.main.temp,
            wind_speed = entity.wind.speed,
            weather_icon = entity.weather.first().icon,
            weather_main = entity.weather.first().main,
        )
    }

    override fun mapToEntity(domain: City): WeatherCityResponse {
        return WeatherCityResponse(
            id = domain.id,
            main = WeatherCityResponse.Main(
                domain.main_humidity,
                domain.main_temp
            ),
            name = domain.name,
            weather = listOf(
                WeatherCityResponse.AllWeatherModel(
                    icon = domain.weather_icon,
                    main = domain.weather_main
                )
            ),
            wind = WeatherCityResponse.Wind(
                domain.wind_speed
            ),
            cod = "200"
        )
    }
}