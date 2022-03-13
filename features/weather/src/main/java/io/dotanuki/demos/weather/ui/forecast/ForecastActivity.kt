package io.dotanuki.demos.weather.ui.forecast

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import io.dotanuki.demos.common.android.selfBind
import io.dotanuki.demos.weather.presentation.forecast.ForecastScreenInteraction
import io.dotanuki.demos.weather.presentation.forecast.ForecastViewModel
import kotlinx.coroutines.launch
import org.kodein.di.DIAware
import org.kodein.di.instance

class ForecastActivity : AppCompatActivity(), DIAware {

    override val di by selfBind()

    private val viewModel by instance<ForecastViewModel>()
    private val screen by instance<ForecastScreen>()

    private val callbacks = object : ForecastScreen.Callbacks {
        override fun onRefresh() {
            viewModel.handle(ForecastScreenInteraction.OpenedScreen)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootView = screen.link(this, callbacks)
        setContentView(rootView)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.run {
                    handle(ForecastScreenInteraction.OpenedScreen)
                    bind().collect { newState ->
                        screen.update(newState)
                    }
                }
            }
        }
    }
}
