package io.dotanuki.demos.weather.ui.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import io.dotanuki.demos.common.android.selfBind
import io.dotanuki.demos.weather.navigation.WeatherNavigator
import io.dotanuki.demos.weather.presentation.details.WeatherDetailsScreenInteraction
import io.dotanuki.demos.weather.presentation.details.WeatherDetailsScreenInteraction.RequestedImageDownload
import io.dotanuki.demos.weather.presentation.details.WeatherDetailsViewModel
import kotlinx.coroutines.launch
import org.kodein.di.DIAware
import org.kodein.di.instance

class WeatherDetailsActivity : AppCompatActivity(), DIAware {

    override val di by selfBind()

    private val viewModel by instance<WeatherDetailsViewModel>()
    private val screen by instance<WeatherDetailsScreen>()

    private val extras by lazy {
        WeatherNavigator(this).unwrap(requireNotNull(intent.extras))
    }

    private val callbacks = object : WeatherDetailsScreen.Callbacks {
        override fun onDownloadRequested() {
            viewModel.handle(RequestedImageDownload(extras))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootView = screen.link(this, callbacks)
        setContentView(rootView)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.run {
                    handle(WeatherDetailsScreenInteraction.OpenedScreen(extras))
                    bind().collect { newState ->
                        screen.update(newState)
                    }
                }
            }
        }
    }
}
