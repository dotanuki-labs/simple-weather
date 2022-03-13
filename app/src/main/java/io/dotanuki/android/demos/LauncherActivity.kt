package io.dotanuki.android.demos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import io.dotanuki.bootstrap.common.android.selfBind
import io.dotanuki.bootstrap.navigator.Navigator
import io.dotanuki.bootstrap.navigator.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kodein.di.DIAware
import org.kodein.di.instance
import android.R.anim.fade_in as FadeIn
import android.R.anim.fade_out as FadeOut

class LauncherActivity : AppCompatActivity(), DIAware {

    override val di by selfBind()

    private val navigator by instance<Navigator>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_launcher)

        lifecycleScope.launch {
            delay(1000)
            proceed()
        }
    }

    private fun proceed() {
        navigator.navigateTo(Screen.FactsList)
        overridePendingTransition(FadeIn, FadeOut)
        finish()
    }
}
