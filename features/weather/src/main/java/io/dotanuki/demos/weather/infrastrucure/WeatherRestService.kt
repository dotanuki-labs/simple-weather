package io.dotanuki.demos.weather.infrastrucure

import retrofit2.http.GET

interface WeatherRestService {

    @GET("/api/forecast") suspend fun getForecast(): List<RawWeather>

    companion object {
        const val BASE_URL = "https://5c5c8ba58d018a0014aa1b24.mockapi.io"
    }
}
