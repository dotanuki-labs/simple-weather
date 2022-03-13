package io.dotanuki.bootstrap.listing.infrastrucure

import kotlinx.serialization.Serializable

@Serializable
data class RawQuotesResponse(
    val categories: List<String>? = null,
    val quotes: List<RawQuote>? = null
)

@Serializable
data class RawQuote(
    val author: String? = null,
    val content: String? = null,
    val category: String? = null,
)
