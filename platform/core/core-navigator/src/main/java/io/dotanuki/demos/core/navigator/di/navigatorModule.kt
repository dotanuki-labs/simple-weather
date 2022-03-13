package io.dotanuki.demos.core.navigator.di

import io.dotanuki.demos.common.kodein.KodeinTags
import io.dotanuki.demos.core.navigator.Navigator
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
