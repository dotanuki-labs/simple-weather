package io.dotanuki.demos.weather.domain

enum class RainExpectation {
    HIGH,
    LOW
}

data class Weather(
    val daysAhead: Int,
    val description: String,
    val maxTemperature: Int,
    val minTemperature: Int,
    val rainExpectation: RainExpectation,
    val relatedImageUrl: String
)
