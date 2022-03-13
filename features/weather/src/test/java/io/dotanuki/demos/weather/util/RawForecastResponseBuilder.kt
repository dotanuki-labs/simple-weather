package io.dotanuki.demos.weather.util

import io.dotanuki.demos.weather.infrastrucure.RawWeather
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object RawForecastResponseBuilder {

    private val baseline = listOf(
        RawWeather(
            day = "1",
            description = "Sunny",
            sunrise = 27420,
            sunset = 63600,
            chanceRain = 0.1f,
            high = 15,
            low = 10,
            image = "https://link.to/image.jpg",
        ),
        RawWeather(
            day = "2",
            description = "Cloudy with showers",
            sunrise = 27540,
            sunset = 63540,
            chanceRain = 0.6f,
            high = 17,
            low = 10,
            image = "https://link.to/image.jpg",
        )
    )

    fun standard(): String = Json.encodeToString(baseline)
}
