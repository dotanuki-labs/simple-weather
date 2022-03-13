package io.dotanuki.demos.weather.ui

import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import iio.dotanuki.demos.weather.databinding.ActivityQuotesBinding
import io.dotanuki.demos.weather.presentation.ForecastPage
import io.dotanuki.demos.weather.presentation.ForecastScreenState
import io.dotanuki.demos.weather.presentation.ForecastScreenState.Failed
import io.dotanuki.demos.weather.presentation.ForecastScreenState.Idle
import io.dotanuki.demos.weather.presentation.ForecastScreenState.Loading
import io.dotanuki.demos.weather.presentation.ForecastScreenState.Success

interface ForecastScreen {

    interface Callbacks {
        fun onRefresh()
    }

    fun link(host: AppCompatActivity, callbacks: Callbacks): View

    fun update(newState: ForecastScreenState)
}

class WrappedForecastScreen : ForecastScreen {

    private lateinit var hostActivity: AppCompatActivity
    private lateinit var bindings: ActivityQuotesBinding
    private lateinit var screenCallbacks: ForecastScreen.Callbacks

    override fun link(host: AppCompatActivity, callbacks: ForecastScreen.Callbacks): View {
        hostActivity = host
        screenCallbacks = callbacks
        bindings = ActivityQuotesBinding.inflate(hostActivity.layoutInflater)
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
            listingErrorState.visibility = View.GONE
            listingSwipeToRefresh.isRefreshing = false
            listingSwipeToRefresh.setOnRefreshListener { screenCallbacks.onRefresh() }
            listingRecyclerView.layoutManager = LinearLayoutManager(hostActivity)
        }
    }

    private fun showExecuting() {
        bindings.run {
            listingSwipeToRefresh.isRefreshing = true
        }
    }

    private fun showResults(pages: List<ForecastPage>) {
        bindings.run {
            listingSwipeToRefresh.isRefreshing = false
            Log.v("Results", pages.toString())
        }
    }

    private fun showErrorState(reason: Throwable) {
        bindings.run {
            listingErrorState.visibility = View.VISIBLE
            listingSwipeToRefresh.isRefreshing = true
        }
    }
}
