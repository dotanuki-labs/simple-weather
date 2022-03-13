package io.dotanuki.demos.weather.infrastrucure

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RawWeather(
    val day: String,
    val description: String,
    val sunrise: Int,
    val sunset: Int,
    @SerialName("chance_rain") val chanceRain: Float,
    val high: Int,
    val low: Int,
    val image: String,
)
