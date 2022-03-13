package io.dotanuki.demos.weather.ui.forecast

import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import iio.dotanuki.demos.weather.databinding.ActivityForecastBinding
import io.dotanuki.demos.weather.presentation.forecast.ForecastPage
import io.dotanuki.demos.weather.presentation.forecast.ForecastScreenState
import io.dotanuki.demos.weather.presentation.forecast.ForecastScreenState.Failed
import io.dotanuki.demos.weather.presentation.forecast.ForecastScreenState.Idle
import io.dotanuki.demos.weather.presentation.forecast.ForecastScreenState.Loading
import io.dotanuki.demos.weather.presentation.forecast.ForecastScreenState.Success

interface ForecastScreen {

    interface Callbacks {
        fun onRefresh()
    }

    fun link(host: AppCompatActivity, callbacks: Callbacks): View

    fun update(newState: ForecastScreenState)
}

class WrappedForecastScreen : ForecastScreen {

    private lateinit var hostActivity: AppCompatActivity
    private lateinit var bindings: ActivityForecastBinding
    private lateinit var screenCallbacks: ForecastScreen.Callbacks

    override fun link(host: AppCompatActivity, callbacks: ForecastScreen.Callbacks): View {
        hostActivity = host
        screenCallbacks = callbacks
        bindings = ActivityForecastBinding.inflate(hostActivity.layoutInflater)
        return bindings.root
    }

    override fun update(newState: ForecastScreenState) {
        when (newState) {
            Idle -> preExecution()
            Loading -> showExecuting()
            is Failed -> showErrorState(newState.reason)
            is Success -> showResults(newState.pages)
        }
    }

    private fun preExecution() {
        bindings.run {
            errorStateContainer.visibility = View.GONE
            swipeToRefresh.isRefreshing = false
            swipeToRefresh.setOnRefreshListener { screenCallbacks.onRefresh() }
        }
    }

    private fun showExecuting() {
        bindings.run {
            swipeToRefresh.isRefreshing = true
        }
    }

    private fun showResults(pages: List<ForecastPage>) {
        bindings.run {
            swipeToRefresh.isRefreshing = false
            forecastPager.adapter = ForecastPagerAdapter(pages)

            TabLayoutMediator(pagerTabs, forecastPager) { tab, position ->
                tab.text = hostActivity.getString(pages[position].titleResource)
            }.attach()
        }
    }

    private fun showErrorState(reason: Throwable) {
        Log.e("ForecastActivity", "Failed on loading forecasts", reason)
        bindings.run {
            errorStateContainer.visibility = View.VISIBLE
            swipeToRefresh.isRefreshing = true
        }
    }
}
