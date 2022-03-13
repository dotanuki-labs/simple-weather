package io.dotanuki.demos.weather.domain

import io.dotanuki.demos.weather.infrastrucure.ImagesInfrastructure
import io.dotanuki.demos.weather.infrastrucure.WeatherInfrastructure

class ForecastRetriever(
    private val weatherInfrastructure: WeatherInfrastructure,
    private val imagesInfrastructure: ImagesInfrastructure
) {

    suspend fun retrieve(): List<Weather> =
        weatherInfrastructure.forecastForTenDays().map {
            it.copy(
                relatedImage = RelatedImage(
                    it.relatedImage.relatedImageUrl,
                    imagesInfrastructure.retrieveIfDownloaded(it.relatedImage.relatedImageUrl)
                )
            )
        }
}
