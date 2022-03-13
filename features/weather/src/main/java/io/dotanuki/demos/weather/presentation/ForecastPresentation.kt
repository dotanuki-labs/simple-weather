package io.dotanuki.demos.weather.presentation

import iio.dotanuki.demos.weather.R
import io.dotanuki.demos.weather.domain.RainExpectation
import io.dotanuki.demos.weather.domain.RelatedImage
import io.dotanuki.demos.weather.domain.Weather

data class ForecastPage(
    val titleResource: Int,
    val content: ForecastPageContent
)

sealed class ForecastPageContent {
    data class Formatted(val rows: List<WeatherRow>) : ForecastPageContent()
    object Empty : ForecastPageContent()
}

data class WeatherRow(
    val title: String,
    val relatedImage: RelatedImage
)

fun List<Weather>.toPages(): List<ForecastPage> {

    fun Weather.toRow(): WeatherRow = WeatherRow("Day $daysAhead : $description", relatedImage)

    val upcoming = ForecastPage(
        titleResource = R.string.forecast_page_upcoming,
        content = ForecastPageContent.Formatted(map { it.toRow() })
    )

    val hottestDays = filter { it.rainExpectation == RainExpectation.LOW }

    val hottest = ForecastPage(
        titleResource = R.string.forecast_page_hottest,
        content = when {
            hottestDays.isEmpty() -> ForecastPageContent.Empty
            else -> ForecastPageContent.Formatted(hottestDays.map { it.toRow() })
        }
    )

    return listOf(upcoming, hottest)
}
