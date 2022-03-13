package io.dotanuki.demos.weather.domain

data class Quote(
    val author: String,
    val content: String
)

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
