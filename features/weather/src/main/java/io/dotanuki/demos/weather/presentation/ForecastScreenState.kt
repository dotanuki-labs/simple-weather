package io.dotanuki.demos.weather.presentation

import io.dotanuki.demos.weather.domain.Quote

sealed class ForecastScreenState {
    object Idle : ForecastScreenState()
    object Loading : ForecastScreenState()
    data class Success(val quotes: List<Quote>) : ForecastScreenState()
    data class Failed(val reason: Throwable) : ForecastScreenState()
}
