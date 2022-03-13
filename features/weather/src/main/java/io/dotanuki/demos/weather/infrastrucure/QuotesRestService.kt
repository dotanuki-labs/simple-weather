package io.dotanuki.demos.weather.infrastrucure

import retrofit2.http.GET

interface QuotesRestService {

    @GET("/v1/quotes") suspend fun getQuotes(): RawQuotesResponse

    companion object {
        const val BASE_URL = "https://android-bootstrap-api.mocklab.io"
    }
}
