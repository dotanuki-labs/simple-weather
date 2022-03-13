package io.dotanuki.bootstrap.listing.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.dotanuki.bootstrap.listing.infrastrucure.QuotesInfrastructure
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class QuotesViewModel(private val infrastructure: QuotesInfrastructure) : ViewModel() {

    private val interactions = Channel<QuotesScreenInteraction>(Channel.UNLIMITED)
    private val states = MutableStateFlow<QuotesScreenState>(QuotesScreenState.Idle)

    fun bind() = states.asStateFlow()

    init {
        viewModelScope.launch {
            interactions.consumeAsFlow().collect {
                showQuotes()
            }
        }
    }

    fun handle(interaction: QuotesScreenInteraction) {
        viewModelScope.launch {
            states.value = QuotesScreenState.Loading
            interactions.send(interaction)
        }
    }

    private suspend fun showQuotes() {
        states.value = try {
            QuotesScreenState.Success(infrastructure.quotes())
        } catch (error: Throwable) {
            QuotesScreenState.Failed(error)
        }
    }
}
