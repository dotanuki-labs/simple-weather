package io.dotanuki.android.demos.navigation

import android.app.Activity
import io.dotanuki.demos.weather.ui.QuotesActivity
import io.dotanuki.demos.core.navigator.Screen

object ScreenLinks {

    val associations by lazy {
        mapOf<Screen, Class<out Activity>>(
            Screen.FactsList to QuotesActivity::class.java
        )
    }
}
