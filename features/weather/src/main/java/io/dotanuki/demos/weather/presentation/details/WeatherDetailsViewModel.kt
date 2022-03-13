package io.dotanuki.demos.weather.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.dotanuki.demos.weather.infrastrucure.ImagesInfrastructure
import io.dotanuki.demos.weather.presentation.details.WeatherDetailsScreenInteraction.OpenedScreen
import io.dotanuki.demos.weather.presentation.details.WeatherDetailsScreenInteraction.RequestedImageDownload
import io.dotanuki.demos.weather.presentation.details.WeatherDetailsScreenState.AvailableInformationDisplayed
import io.dotanuki.demos.weather.presentation.details.WeatherDetailsScreenState.Idle
import io.dotanuki.demos.weather.presentation.details.WeatherDetailsScreenState.ImageDownloadFailed
import io.dotanuki.demos.weather.presentation.details.WeatherDetailsScreenState.ImageDownloaded
import io.dotanuki.demos.weather.presentation.details.WeatherDetailsScreenState.ImageRequested
import io.dotanuki.demos.weather.navigation.WeatherDetailsExtras
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class WeatherDetailsViewModel(private val infrastructure: ImagesInfrastructure) : ViewModel() {

    private val interactions = Channel<WeatherDetailsScreenInteraction>(Channel.UNLIMITED)
    private val states = MutableStateFlow<WeatherDetailsScreenState>(Idle)

    fun bind() = states.asStateFlow()

    init {
        viewModelScope.launch {
            interactions.consumeAsFlow().collect {
                when (it) {
                    is OpenedScreen -> displayAvailable(it.extras)
                    is RequestedImageDownload -> downloadAndDisplayImage(it.extras.imageUrl)
                }
            }
        }
    }

    fun handle(interaction: WeatherDetailsScreenInteraction) {
        viewModelScope.launch {
            interactions.send(interaction)
        }
    }

    private suspend fun downloadAndDisplayImage(imageUrl: String) {
        states.value = ImageRequested

        try {
            val imageFile = infrastructure.retrieve(imageUrl)
            states.value = ImageDownloaded(imageFile)
        } catch (incoming: Throwable) {
            states.value = ImageDownloadFailed(incoming)
        }
    }

    private suspend fun displayAvailable(extras: WeatherDetailsExtras) {
        val imageFile = infrastructure.retrieveIfDownloaded(extras.imageUrl)
        states.value = AvailableInformationDisplayed(imageFile, extras.description)
    }
}
