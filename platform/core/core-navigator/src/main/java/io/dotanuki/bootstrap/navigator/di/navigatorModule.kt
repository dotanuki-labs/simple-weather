package io.dotanuki.bootstrap.navigator.di

import io.dotanuki.bootstrap.common.kodein.KodeinTags
import io.dotanuki.bootstrap.navigator.Navigator
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider

val navigatorModule = DI.Module("navigator") {
    bind {
        provider {
            Navigator(
                host = instance(KodeinTags.hostActivity),
                links = instance()
            )
        }
    }
}
