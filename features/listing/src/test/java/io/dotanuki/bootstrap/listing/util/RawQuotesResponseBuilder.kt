package io.dotanuki.bootstrap.listing.util

import io.dotanuki.bootstrap.listing.infrastrucure.RawQuote
import io.dotanuki.bootstrap.listing.infrastrucure.RawQuotesResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object RawQuotesResponseBuilder {

    private val baseline = RawQuotesResponse(
        categories = listOf("love", "motivation"),
        quotes = listOf(
            RawQuote(
                author = "Franklin D. Roosevelt",
                content = "When you reach the end of your rope, tie a knot in it and hang on",
                category = "motivation",
            ),
            RawQuote(
                author = "Mother Teresa",
                content = "If you judge people, you have no time to love them",
                category = "love",
            ),
            RawQuote(
                author = "Albert Einstein",
                content = "Insanity: doing the same thing over and over again and expecting different results",
                category = "motivation",
            ),
        )
    )

    fun standard(): String = Json.encodeToString(baseline)

    fun missingFields(): String = RawQuotesResponse(
        categories = baseline.categories,
        quotes = baseline.quotes?.map { it.copy(author = null) }
    ).let {
        Json.encodeToString(it)
    }
}
