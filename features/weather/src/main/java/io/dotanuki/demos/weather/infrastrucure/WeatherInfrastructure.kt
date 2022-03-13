package io.dotanuki.demos.weather.infrastrucure

import io.dotanuki.demos.core.networking.managedExecution
import io.dotanuki.demos.weather.domain.ComputeRainExpectation
import io.dotanuki.demos.weather.domain.Weather

class WeatherInfrastructure(private val service: WeatherRestService) {

    suspend fun forecastForTenDays(): List<Weather> = managedExecution {
        service.getForecast().toWeather()
    }

    private fun List<RawWeather>.toWeather(): List<Weather> =
        map {
            Weather(
                daysAhead = it.day.toInt(),
                description = it.description,
                maxTemperature = it.high,
                minTemperature = it.low,
                relatedImageUrl = it.image,
                rainExpectation = ComputeRainExpectation(it.chanceRain)
            )
        }
}
