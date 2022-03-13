package io.dotanuki.demos.weather.ui

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import iio.dotanuki.demos.weather.R
import io.dotanuki.demos.core.networking.errors.RemoteServiceIntegrationError
import io.dotanuki.demos.testing.app.TestApplication
import io.dotanuki.demos.testing.app.whenActivityResumed
import io.dotanuki.demos.weather.di.weatherModule
import io.dotanuki.demos.weather.domain.RelatedImage
import io.dotanuki.demos.weather.presentation.forecast.ForecastPage
import io.dotanuki.demos.weather.presentation.forecast.ForecastPageContent
import io.dotanuki.demos.weather.presentation.forecast.ForecastScreenState
import io.dotanuki.demos.weather.presentation.forecast.ForecastScreenState.Idle
import io.dotanuki.demos.weather.presentation.forecast.ForecastScreenState.Loading
import io.dotanuki.demos.weather.presentation.forecast.WeatherRow
import io.dotanuki.demos.weather.ui.FakeForecastScreen.Companion.quotesScreen
import io.dotanuki.demos.weather.ui.forecast.ForecastActivity
import io.dotanuki.demos.weather.util.RawForecastResponseBuilder
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ForecastActivityTests {

    lateinit var screen: FakeForecastScreen
    lateinit var server: MockWebServer

    @Before fun `before each test`() {
        server = MockWebServer()
        val testApp = TestApplication.setupWith(weatherModule, quotesTestModule(server))
        screen = testApp.quotesScreen()
    }

    @After fun `after each test`() {
        server.shutdown()
    }

    @Test fun `should show available forecast`() {
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(RawForecastResponseBuilder.standard())
        )

        whenActivityResumed<ForecastActivity> {
            val expectedPages = listOf(
                ForecastPage(
                    titleResource = R.string.forecast_page_upcoming,
                    ForecastPageContent.Formatted(
                        listOf(
                            WeatherRow("Day 1 : Sunny", RelatedImage("https://link.to/image.jpg")),
                            WeatherRow("Day 2 : Cloudy with showers", RelatedImage("https://link.to/image.jpg")),
                        )
                    )
                ),
                ForecastPage(
                    titleResource = R.string.forecast_page_hottest,
                    ForecastPageContent.Formatted(
                        listOf(
                            WeatherRow("Day 1 : Sunny", RelatedImage("https://link.to/image.jpg")),
                        )
                    )
                )
            )

            val finalState = ForecastScreenState.Success(expectedPages)
            val expectedStates = listOf(Idle, Loading, finalState)
            assertThat(screen.trackedStates).isEqualTo(expectedStates)
        }
    }

    @Ignore @Test fun `should show no upcoming hot days`() {
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(RawForecastResponseBuilder.noHotDays())
        )

        whenActivityResumed<ForecastActivity> {
            val expectedPages = listOf(
                ForecastPage(
                    titleResource = R.string.forecast_page_upcoming,
                    ForecastPageContent.Formatted(
                        listOf(
                            WeatherRow("Day 1 : Sunny", RelatedImage("https://link.to/image.jpg")),
                            WeatherRow("Day 2 : Cloudy with showers", RelatedImage("https://link.to/image.jpg")),
                        )
                    )
                ),
                ForecastPage(
                    titleResource = R.string.forecast_page_hottest,
                    ForecastPageContent.Empty
                )
            )

            val finalState = ForecastScreenState.Success(expectedPages)
            val expectedStates = listOf(Idle, Loading, finalState)
            assertThat(screen.trackedStates).isEqualTo(expectedStates)
        }
    }

    @Test fun `when remote service fails, should display the error`() {
        server.enqueue(
            MockResponse().setResponseCode(500)
        )

        whenActivityResumed<ForecastActivity> {
            val incomingError = RemoteServiceIntegrationError.RemoteSystem
            val finalState = ForecastScreenState.Failed(incomingError)
            val expectedStates = listOf(Idle, Loading, finalState)
            assertThat(screen.trackedStates).isEqualTo(expectedStates)
        }
    }
}
