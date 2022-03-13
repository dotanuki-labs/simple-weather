package io.dotanuki.bootstrap.listing.ui

import io.dotanuki.bootstrap.listing.infrastrucure.QuotesRestService
import io.dotanuki.bootstrap.networking.RestServiceBuilder
import okhttp3.mockwebserver.MockWebServer
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

fun quotesTestModule(server: MockWebServer) = DI.Module("quotes-test-module") {

    bind(overrides = true) {
        singleton {
            RestServiceBuilder.build<QuotesRestService>(server.url("/"))
        }
    }

    bind<QuotesScreen>(overrides = true) {
        // Make sure the same instance is provided to both FactsActivity and Tests
        singleton {
            FakeQuotesScreen()
        }
    }
}
