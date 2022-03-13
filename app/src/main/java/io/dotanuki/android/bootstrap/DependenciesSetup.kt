package io.dotanuki.android.bootstrap

import android.app.Application
import io.dotanuki.android.bootstrap.di.applicationModule
import io.dotanuki.bootstrap.listing.di.quotesModule
import io.dotanuki.bootstrap.navigator.di.navigatorModule
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

class DependenciesSetup(private val app: Application) {

    val container by lazy {
        DI {
            modules.forEach { import(it) }
            bind {
                singleton { app }
            }
        }
    }

    private val modules = listOf(
        applicationModule,
        navigatorModule,
        quotesModule
    )
}
