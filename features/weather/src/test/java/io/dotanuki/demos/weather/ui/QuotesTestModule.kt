package io.dotanuki.demos.weather.ui

import io.dotanuki.demos.weather.infrastrucure.WeatherRestService
import io.dotanuki.demos.core.networking.RestServiceBuilder
import okhttp3.mockwebserver.MockWebServer
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

fun quotesTestModule(server: MockWebServer) = DI.Module("quotes-test-module") {

    bind(overrides = true) {
        singleton {
            RestServiceBuilder.build<WeatherRestService>(server.url("/"))
        }
    }

    bind<QuotesScreen>(overrides = true) {
        // Make sure the same instance is provided to both FactsActivity and Tests
        singleton {
            FakeQuotesScreen()
        }
    }
}
