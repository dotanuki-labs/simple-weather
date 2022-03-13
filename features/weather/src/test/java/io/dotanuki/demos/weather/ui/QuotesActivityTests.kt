package io.dotanuki.demos.weather.ui

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import io.dotanuki.demos.weather.di.quotesModule
import io.dotanuki.demos.weather.domain.Quote
import io.dotanuki.demos.weather.presentation.QuotesScreenState
import io.dotanuki.demos.weather.presentation.QuotesScreenState.Idle
import io.dotanuki.demos.weather.presentation.QuotesScreenState.Loading
import io.dotanuki.demos.weather.ui.FakeQuotesScreen.Companion.quotesScreen
import io.dotanuki.demos.weather.util.RawQuotesResponseBuilder
import io.dotanuki.bootstrap.networking.errors.RemoteServiceIntegrationError
import io.dotanuki.bootstrap.testing.app.TestApplication
import io.dotanuki.bootstrap.testing.app.whenActivityResumed
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QuotesActivityTests {

    lateinit var screen: FakeQuotesScreen
    lateinit var server: MockWebServer

    @Before fun `before each test`() {
        server = MockWebServer()
        val testApp = TestApplication.setupWith(quotesModule, quotesTestModule(server))
        screen = testApp.quotesScreen()
    }

    @After fun `after each test`() {
        server.shutdown()
    }

    @Test fun `should display available quotes`() {
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(RawQuotesResponseBuilder.standard())
        )

        whenActivityResumed<QuotesActivity> {
            val expectedQuotes = listOf(
                Quote(
                    "Franklin D. Roosevelt",
                    "When you reach the end of your rope, tie a knot in it and hang on"
                ),
                Quote(
                    "Mother Teresa",
                    "If you judge people, you have no time to love them"
                ),
                Quote(
                    "Albert Einstein",
                    "Insanity: doing the same thing over and over again and expecting different results"
                )
            )

            val finalState = QuotesScreenState.Success(expectedQuotes)
            val expectedStates = listOf(Idle, Loading, finalState)
            assertThat(screen.trackedStates).isEqualTo(expectedStates)
        }
    }

    @Test fun `when remote service fails, should display the error`() {
        server.enqueue(
            MockResponse().setResponseCode(500)
        )

        whenActivityResumed<QuotesActivity> {
            val incomingError = RemoteServiceIntegrationError.RemoteSystem
            val finalState = QuotesScreenState.Failed(incomingError)
            val expectedStates = listOf(Idle, Loading, finalState)
            assertThat(screen.trackedStates).isEqualTo(expectedStates)
        }
    }
}
