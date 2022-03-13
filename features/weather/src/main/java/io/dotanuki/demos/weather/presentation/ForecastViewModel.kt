package io.dotanuki.demos.weather.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.dotanuki.demos.weather.domain.ForecastRetriever
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class ForecastViewModel(private val retriever: ForecastRetriever) : ViewModel() {

    private val interactions = Channel<ForecastScreenInteraction>(Channel.UNLIMITED)
    private val states = MutableStateFlow<ForecastScreenState>(ForecastScreenState.Idle)

    fun bind() = states.asStateFlow()

    init {
        viewModelScope.launch {
            interactions.consumeAsFlow().collect {
                showForecasts()
            }
        }
    }

    fun handle(interaction: ForecastScreenInteraction) {
        viewModelScope.launch {
            states.value = ForecastScreenState.Loading
            interactions.send(interaction)
        }
    }

    private suspend fun showForecasts() {
        states.value = try {
            val forecast = retriever.retrieve()
            ForecastScreenState.Success(forecast.toPages())
        } catch (error: Throwable) {
            ForecastScreenState.Failed(error)
        }
    }
}
