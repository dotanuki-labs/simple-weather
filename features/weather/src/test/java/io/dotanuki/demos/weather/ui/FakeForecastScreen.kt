package io.dotanuki.demos.weather.ui

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.dotanuki.demos.weather.presentation.ForecastScreenState
import io.dotanuki.demos.testing.app.TestApplication
import org.kodein.di.direct
import org.kodein.di.instance

class FakeForecastScreen : ForecastScreen {

    val trackedStates = mutableListOf<ForecastScreenState>()
    var isLinked: Boolean = false

    override fun link(host: AppCompatActivity, callbacks: ForecastScreen.Callbacks): View {
        isLinked = true
        return View(host)
    }

    override fun update(newState: ForecastScreenState) {
        trackedStates += newState
    }

    companion object {
        fun TestApplication.quotesScreen(): FakeForecastScreen =
            di.direct.instance<ForecastScreen>() as FakeForecastScreen
    }
}
