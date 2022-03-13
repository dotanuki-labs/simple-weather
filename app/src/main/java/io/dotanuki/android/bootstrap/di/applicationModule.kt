package io.dotanuki.android.bootstrap.di

import io.dotanuki.android.bootstrap.navigation.ScreenLinks
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
