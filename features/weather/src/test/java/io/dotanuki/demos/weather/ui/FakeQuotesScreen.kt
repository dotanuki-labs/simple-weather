package io.dotanuki.demos.weather.ui

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.dotanuki.demos.weather.presentation.QuotesScreenState
import io.dotanuki.demos.testing.app.TestApplication
import org.kodein.di.direct
import org.kodein.di.instance

class FakeQuotesScreen : QuotesScreen {

    val trackedStates = mutableListOf<QuotesScreenState>()
    var isLinked: Boolean = false

    override fun link(host: AppCompatActivity, callbacks: QuotesScreen.Callbacks): View {
        isLinked = true
        return View(host)
    }

    override fun update(newState: QuotesScreenState) {
        trackedStates += newState
    }

    companion object {
        fun TestApplication.quotesScreen(): FakeQuotesScreen =
            di.direct.instance<QuotesScreen>() as FakeQuotesScreen
    }
}
