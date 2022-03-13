package io.dotanuki.demos.weather.presentation.details

import io.dotanuki.demos.weather.ui.navigation.WeatherDetailsExtras

sealed class WeatherDetailsScreenInteraction {
    data class OpenedScreen(val extras: WeatherDetailsExtras) : WeatherDetailsScreenInteraction()
    data class RequestedImageDownload(val extras: WeatherDetailsExtras) : WeatherDetailsScreenInteraction()
}
