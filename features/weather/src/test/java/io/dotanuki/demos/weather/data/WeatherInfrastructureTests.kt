package io.dotanuki.demos.weather.data

import com.google.common.truth.Truth.assertAbout
import com.google.common.truth.Truth.assertThat
import io.dotanuki.demos.core.networking.RestServiceBuilder
import io.dotanuki.demos.core.networking.errors.RemoteServiceIntegrationError
import io.dotanuki.demos.testing.commons.truth.EspeculativeExecution
import io.dotanuki.demos.weather.domain.RainExpectation
import io.dotanuki.demos.weather.domain.RelatedImage
import io.dotanuki.demos.weather.domain.Weather
import io.dotanuki.demos.weather.infrastrucure.WeatherInfrastructure
import io.dotanuki.demos.weather.util.RawForecastResponseBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

class WeatherInfrastructureTests {

    lateinit var mockServer: MockWebServer
    lateinit var infrastructure: WeatherInfrastructure

    @Before fun `before each test`() {
        mockServer = MockWebServer()
        infrastructure = WeatherInfrastructure(
            RestServiceBuilder.build(mockServer.url("/"))
        )
    }

    @After fun `after each test`() {
        mockServer.shutdown()
    }

    @Test fun `should retrieve forecast with success`() = runBlocking {
        val rawForecast = RawForecastResponseBuilder.standard()
        mockServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(rawForecast)
        )

        val retrivedForecast = infrastructure.forecastForTenDays()

        val expectedForecast = listOf(
            Weather(
                daysAhead = 1,
                description = "Sunny",
                maxTemperature = 15,
                minTemperature = 10,
                relatedImage = RelatedImage("https://link.to/image.jpg"),
                rainExpectation = RainExpectation.LOW
            ),
            Weather(
                daysAhead = 2,
                description = "Cloudy with showers",
                maxTemperature = 17,
                minTemperature = 10,
                relatedImage = RelatedImage("https://link.to/image.jpg"),
                rainExpectation = RainExpectation.HIGH
            )
        )

        assertThat(retrivedForecast).isEqualTo(expectedForecast)
    }

    @Test fun `should fail with http issues`() = runBlocking {
        mockServer.enqueue(
            MockResponse().setResponseCode(503)
        )

        val execution = EspeculativeExecution {
            runBlocking { infrastructure.forecastForTenDays() }
        }

        val expectedError = RemoteServiceIntegrationError.RemoteSystem

        assertAbout(execution).that(expectedError).hasBeingThrown()
    }
}
