package io.dotanuki.demos.weather.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherDetailsExtras(
    val description: String,
    val imageUrl: String
) : Parcelable
