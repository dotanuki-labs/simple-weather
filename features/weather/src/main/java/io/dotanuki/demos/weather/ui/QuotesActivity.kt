package io.dotanuki.demos.weather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import io.dotanuki.demos.common.android.selfBind
import io.dotanuki.demos.weather.presentation.QuotesScreenInteraction
import io.dotanuki.demos.weather.presentation.QuotesViewModel
import kotlinx.coroutines.launch
import org.kodein.di.DIAware
import org.kodein.di.instance

class QuotesActivity : AppCompatActivity(), DIAware {

    override val di by selfBind()

    private val viewModel by instance<QuotesViewModel>()
    private val screen by instance<QuotesScreen>()

    private val callbacks = object : QuotesScreen.Callbacks {
        override fun onRefresh() {
            viewModel.handle(QuotesScreenInteraction.OpenedScreen)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootView = screen.link(this, callbacks)
        setContentView(rootView)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.run {
                    handle(QuotesScreenInteraction.OpenedScreen)
                    bind().collect { newState ->
                        screen.update(newState)
                    }
                }
            }
        }
    }
}
