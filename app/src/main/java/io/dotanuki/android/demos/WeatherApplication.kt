package io.dotanuki.android.demos

import android.app.Application
import org.kodein.di.DIAware

class WeatherApplication : Application(), DIAware {

    override val di = DependenciesSetup(this).container
}
