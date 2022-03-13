package io.dotanuki.demos.weather.domain

import java.io.File

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
    val relatedImage: RelatedImage
)

data class RelatedImage(
    val relatedImageUrl: String,
    val downloadedFile: File? = null
)
