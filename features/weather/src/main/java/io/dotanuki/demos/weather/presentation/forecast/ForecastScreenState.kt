package io.dotanuki.demos.weather.presentation.forecast

sealed class ForecastScreenState {
    object Idle : ForecastScreenState()
    object Loading : ForecastScreenState()
    data class Success(val pages: List<ForecastPage>) : ForecastScreenState()
    data class Failed(val reason: Throwable) : ForecastScreenState()
}
