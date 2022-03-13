package io.dotanuki.bootstrap.listing.presentation

import io.dotanuki.bootstrap.listing.domain.Quote

sealed class QuotesScreenState {
    object Idle : QuotesScreenState()
    object Loading : QuotesScreenState()
    data class Success(val quotes: List<Quote>) : QuotesScreenState()
    data class Failed(val reason: Throwable) : QuotesScreenState()
}
