package io.dotanuki.demos.weather.navigation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.dotanuki.demos.weather.ui.details.WeatherDetailsActivity

class WeatherNavigator(private val host: AppCompatActivity) {

    fun toDetails(content: String) {
        val intent = Intent(host, WeatherDetailsActivity::class.java)
        intent.putExtra("extras", WeatherDetailsExtras(content, "https://picsum.photos/640/480"))
        host.startActivity(intent)
    }

    fun unwrap(bundle: Bundle): WeatherDetailsExtras = requireNotNull(bundle.getParcelable("extras"))
}
