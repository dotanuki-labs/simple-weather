package io.dotanuki.demos.weather.ui.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherDetailsExtras(
    val description: String,
    val imageUrl: String
) : Parcelable
