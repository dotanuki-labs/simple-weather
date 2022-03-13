package io.dotanuki.android.bootstrap.navigation

import android.app.Activity
import io.dotanuki.bootstrap.listing.ui.QuotesActivity
import io.dotanuki.bootstrap.navigator.Screen

object ScreenLinks {

    val associations by lazy {
        mapOf<Screen, Class<out Activity>>(
            Screen.FactsList to QuotesActivity::class.java
        )
    }
}
