package io.dotanuki.demos.weather.di

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.dotanuki.demos.common.kodein.KodeinTags
import io.dotanuki.demos.core.networking.RestServiceBuilder
import io.dotanuki.demos.weather.domain.ForecastRetriever
import io.dotanuki.demos.weather.infrastrucure.ImagesInfrastructure
import io.dotanuki.demos.weather.infrastrucure.WeatherInfrastructure
import io.dotanuki.demos.weather.infrastrucure.WeatherRestService
import io.dotanuki.demos.weather.presentation.forecast.ForecastViewModel
import io.dotanuki.demos.weather.presentation.details.WeatherDetailsViewModel
import io.dotanuki.demos.weather.ui.forecast.ForecastScreen
import io.dotanuki.demos.weather.ui.forecast.WrappedForecastScreen
import io.dotanuki.demos.weather.ui.details.WeatherDetailsScreen
import io.dotanuki.demos.weather.ui.details.WrappedWeatherDetailsScreen
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
        provider { WeatherInfrastructure(instance()) }
    }

    bind {
        provider {
            val host: FragmentActivity = instance(KodeinTags.hostActivity)
            ImagesInfrastructure(host.cacheDir)
        }
    }

    bind {
        provider {
            ForecastRetriever(instance(), instance())
        }
    }

    bind {
        provider {
            @Suppress("UNCHECKED_CAST") val factory = object : ViewModelProvider.Factory {

                val weatherInfra = WeatherInfrastructure(instance())

                override fun <VM : ViewModel> create(modelClass: Class<VM>) =
                    ForecastViewModel(instance()) as VM
            }

            val host: FragmentActivity = instance(KodeinTags.hostActivity)
            ViewModelProvider(host, factory)[ForecastViewModel::class.java]
        }
    }

    bind {
        provider {
            @Suppress("UNCHECKED_CAST") val factory = object : ViewModelProvider.Factory {
                override fun <VM : ViewModel> create(modelClass: Class<VM>) =
                    WeatherDetailsViewModel(instance()) as VM
            }

            val host: FragmentActivity = instance(KodeinTags.hostActivity)
            ViewModelProvider(host, factory)[WeatherDetailsViewModel::class.java]
        }
    }

    bind<ForecastScreen> {
        provider {
            WrappedForecastScreen()
        }
    }

    bind<WeatherDetailsScreen> {
        provider {
            WrappedWeatherDetailsScreen()
        }
    }
}
