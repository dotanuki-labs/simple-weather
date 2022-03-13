package io.dotanuki.android.bootstrap

import android.app.Application
import org.kodein.di.DIAware

class BootstrapApplication : Application(), DIAware {

    override val di = DependenciesSetup(this).container
}
