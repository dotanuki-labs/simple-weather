package io.dotanuki.demos.weather.data

import com.google.common.truth.Truth.assertAbout
import com.google.common.truth.Truth.assertThat
import io.dotanuki.demos.core.networking.RestServiceBuilder
import io.dotanuki.demos.core.networking.errors.RemoteServiceIntegrationError
import io.dotanuki.demos.testing.commons.truth.EspeculativeExecution
import io.dotanuki.demos.weather.domain.Quote
import io.dotanuki.demos.weather.infrastrucure.QuotesInfrastructure
import io.dotanuki.demos.weather.infrastrucure.QuotesInfrastructureError
import io.dotanuki.demos.weather.util.RawQuotesResponseBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

class QuotesInfrastructureTests {

    lateinit var mockServer: MockWebServer
    lateinit var infrastructure: QuotesInfrastructure

    @Before fun `before each test`() {
        mockServer = MockWebServer()
        infrastructure = QuotesInfrastructure(
            RestServiceBuilder.build(mockServer.url("/"))
        )
    }

    @After fun `after each test`() {
        mockServer.shutdown()
    }

    @Test fun `should retrieve quotes with success`() = runBlocking {
        val validQuotes = RawQuotesResponseBuilder.standard()
        mockServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(validQuotes)
        )

        val retrivedQuotes = infrastructure.quotes()
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

        assertThat(retrivedQuotes).isEqualTo(expectedQuotes)
    }

    @Test fun `should fail with incomplete quotes`() = runBlocking {
        val missingAuthors = RawQuotesResponseBuilder.missingFields()
        mockServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(missingAuthors)
        )

        val execution = EspeculativeExecution {
            runBlocking { infrastructure.quotes() }
        }

        val expectedError = QuotesInfrastructureError

        assertAbout(execution).that(expectedError).hasBeingThrown()
    }

    @Test fun `should fail http issues`() = runBlocking {
        mockServer.enqueue(
            MockResponse().setResponseCode(503)
        )

        val execution = EspeculativeExecution {
            runBlocking { infrastructure.quotes() }
        }

        val expectedError = RemoteServiceIntegrationError.RemoteSystem

        assertAbout(execution).that(expectedError).hasBeingThrown()
    }
}
