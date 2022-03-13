package io.dotanuki.android.demos.di

import io.dotanuki.android.demos.navigation.ScreenLinks
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val applicationModule = DI.Module("application") {

    bind {
        singleton {
            ScreenLinks.associations
        }
    }
}
