package io.dotanuki.demos.weather.di

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.dotanuki.demos.common.kodein.KodeinTags
import io.dotanuki.demos.core.networking.RestServiceBuilder
import io.dotanuki.demos.weather.infrastrucure.WeatherInfrastructure
import io.dotanuki.demos.weather.infrastrucure.WeatherRestService
import io.dotanuki.demos.weather.presentation.ForecastViewModel
import io.dotanuki.demos.weather.ui.QuotesScreen
import io.dotanuki.demos.weather.ui.WrappedForecastScreen
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
import org.kodein.di.singleton

val weatherModule = DI.Module("weather-module") {

    bind<WeatherRestService> {
        singleton {
            RestServiceBuilder.build(WeatherRestService.BASE_URL)
        }
    }

    bind {
        provider {
            @Suppress("UNCHECKED_CAST") val factory = object : ViewModelProvider.Factory {

                val infrastructure = WeatherInfrastructure(instance())

                override fun <VM : ViewModel> create(modelClass: Class<VM>) =
                    ForecastViewModel(infrastructure) as VM
            }

            val host: FragmentActivity = instance(KodeinTags.hostActivity)
            ViewModelProvider(host, factory)[ForecastViewModel::class.java]
        }
    }

    bind<QuotesScreen> {
        provider {
            WrappedForecastScreen()
        }
    }
}
