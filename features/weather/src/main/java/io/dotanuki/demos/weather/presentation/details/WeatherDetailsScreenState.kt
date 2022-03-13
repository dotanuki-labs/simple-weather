package io.dotanuki.demos.weather.presentation.details

import java.io.File

sealed class WeatherDetailsScreenState {
    object Idle : WeatherDetailsScreenState()

    data class AvailableInformationDisplayed(
        val imageFile: File?,
        val description: String
    ) : WeatherDetailsScreenState()

    object ImageRequested : WeatherDetailsScreenState()
    data class ImageDownloaded(val imageFile: File) : WeatherDetailsScreenState()
    data class ImageDownloadFailed(val reason: Throwable) : WeatherDetailsScreenState()
}
