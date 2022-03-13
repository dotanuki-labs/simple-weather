package io.dotanuki.demos.weather.presentation

import io.dotanuki.demos.weather.domain.Quote

sealed class QuotesScreenState {
    object Idle : QuotesScreenState()
    object Loading : QuotesScreenState()
    data class Success(val quotes: List<Quote>) : QuotesScreenState()
    data class Failed(val reason: Throwable) : QuotesScreenState()
}
