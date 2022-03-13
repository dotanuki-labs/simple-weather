package io.dotanuki.demos.weather.ui

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.dotanuki.bootstrap.listing.databinding.ActivityQuotesBinding
import io.dotanuki.demos.weather.domain.Quote
import io.dotanuki.demos.weather.presentation.QuotesScreenState
import io.dotanuki.demos.weather.presentation.QuotesScreenState.Failed
import io.dotanuki.demos.weather.presentation.QuotesScreenState.Idle
import io.dotanuki.demos.weather.presentation.QuotesScreenState.Loading
import io.dotanuki.demos.weather.presentation.QuotesScreenState.Success

interface QuotesScreen {

    interface Callbacks {
        fun onRefresh()
    }

    fun link(host: AppCompatActivity, callbacks: Callbacks): View

    fun update(newState: QuotesScreenState)
}

class WrappedQuotesScreen : QuotesScreen {

    private lateinit var hostActivity: AppCompatActivity
    private lateinit var bindings: ActivityQuotesBinding
    private lateinit var screenCallbacks: QuotesScreen.Callbacks

    override fun link(host: AppCompatActivity, callbacks: QuotesScreen.Callbacks): View {
        hostActivity = host
        screenCallbacks = callbacks
        bindings = ActivityQuotesBinding.inflate(hostActivity.layoutInflater)
        return bindings.root
    }

    override fun update(newState: QuotesScreenState) {
        when (newState) {
            Idle -> preExecution()
            Loading -> showExecuting()
            is Failed -> showErrorState(newState.reason)
            is Success -> showResults(newState.quotes)
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

    private fun showResults(quotes: List<Quote>) {
        bindings.run {
            listingSwipeToRefresh.isRefreshing = false
            listingRecyclerView.adapter = QuotesRecyclerAdapter(quotes)
        }
    }

    private fun showErrorState(reason: Throwable) {
        bindings.run {
            listingErrorState.visibility = View.VISIBLE
            listingSwipeToRefresh.isRefreshing = true
        }
    }
}
