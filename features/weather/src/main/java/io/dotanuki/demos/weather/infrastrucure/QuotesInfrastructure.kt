package io.dotanuki.demos.weather.infrastrucure

import io.dotanuki.demos.weather.domain.Quote
import io.dotanuki.demos.core.networking.managedExecution

object QuotesInfrastructureError : Throwable("Missing fields in the expected json response")

class QuotesInfrastructure(private val service: QuotesRestService) {

    suspend fun quotes(): List<Quote> = managedExecution {
        service.getQuotes().unwrap()
    }

    private fun RawQuotesResponse.unwrap(): List<Quote> = try {
        requireNotNull(quotes).map {
            Quote(requireNotNull(it.author), requireNotNull(it.content))
        }
    } catch (incoming: Throwable) {
        throw QuotesInfrastructureError
    }
}
