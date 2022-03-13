package io.dotanuki.demos.weather.ui.details

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import iio.dotanuki.demos.weather.databinding.ActivityWeatherDetailsBinding
import io.dotanuki.demos.weather.presentation.details.WeatherDetailsScreenState
import io.dotanuki.demos.weather.presentation.details.WeatherDetailsScreenState.AvailableInformationDisplayed
import io.dotanuki.demos.weather.presentation.details.WeatherDetailsScreenState.Idle
import io.dotanuki.demos.weather.presentation.details.WeatherDetailsScreenState.ImageDownloadFailed
import io.dotanuki.demos.weather.presentation.details.WeatherDetailsScreenState.ImageDownloaded
import io.dotanuki.demos.weather.presentation.details.WeatherDetailsScreenState.ImageRequested
import java.io.File

interface WeatherDetailsScreen {

    interface Callbacks {
        fun onDownloadRequested()
    }

    fun link(host: AppCompatActivity, callbacks: Callbacks): View

    fun update(newState: WeatherDetailsScreenState)
}

class WrappedWeatherDetailsScreen : WeatherDetailsScreen {

    private lateinit var hostActivity: AppCompatActivity
    private lateinit var bindings: ActivityWeatherDetailsBinding
    private lateinit var screenCallbacks: WeatherDetailsScreen.Callbacks

    override fun link(host: AppCompatActivity, callbacks: WeatherDetailsScreen.Callbacks): View {
        hostActivity = host
        screenCallbacks = callbacks
        bindings = ActivityWeatherDetailsBinding.inflate(hostActivity.layoutInflater)
        return bindings.root
    }

    override fun update(newState: WeatherDetailsScreenState) {
        when (newState) {
            Idle -> setupScreen()
            is AvailableInformationDisplayed -> showContent(newState.description, newState.imageFile)
            is ImageRequested -> showDownloading()
            is ImageDownloaded -> showDownloadedImage(newState.imageFile)
            is ImageDownloadFailed -> reportError(newState.reason)
        }
    }

    private fun reportError(reason: Throwable) {
        Log.e("WeatherDetailsScreen", "Error when downloading image", reason)
        Toast.makeText(hostActivity, "Error when downloading image", Toast.LENGTH_LONG).show()
    }

    private fun showDownloadedImage(imageFile: File) {
        bindings.run {
            imageWeatherPreview.load(imageFile)
            buttonDownloadRequested.visibility = View.INVISIBLE
            buttonDownloadRequested.setOnClickListener(null)
        }
    }

    private fun showDownloading() {
        Toast.makeText(hostActivity, "Downloading Image", Toast.LENGTH_LONG).show()
    }

    private fun showContent(description: String, imageFile: File?) {
        if (imageFile != null) showDownloadedImage(imageFile)
        bindings.textWeatherContent.text = description
    }

    private fun setupScreen() {
        bindings.run {
            buttonDownloadRequested.setOnClickListener {
                screenCallbacks.onDownloadRequested()
            }
        }
    }
}
