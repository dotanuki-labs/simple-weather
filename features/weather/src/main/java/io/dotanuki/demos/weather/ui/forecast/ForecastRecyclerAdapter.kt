package io.dotanuki.demos.weather.ui.forecast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Size
import iio.dotanuki.demos.weather.R
import io.dotanuki.demos.weather.ui.navigation.WeatherNavigator
import io.dotanuki.demos.weather.presentation.forecast.WeatherRow

class ForecastRecyclerAdapter(
    private val navigator: WeatherNavigator,
    private val rows: List<WeatherRow>
) : RecyclerView.Adapter<ForecastRecyclerAdapter.ViewHolder>() {

    class ViewHolder(
        itemView: View,
        private val navigator: WeatherNavigator
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(row: WeatherRow) {
            itemView.run {
                val contentTextView = findViewById<TextView>(R.id.textWeatherContent)
                contentTextView.text = row.title
                setOnClickListener { navigator.toDetails(row.title) }

                findViewById<ImageView>(R.id.imageWeatherPreview).load(row.relatedImage.downloadedFile) {
                    crossfade(true)
                    placeholder(R.drawable.help_circle)
                    error(R.drawable.help_circle)
                    size(Size(64, 48))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_forecast, parent, false)
        return ViewHolder(view, navigator)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(rows[position])

    override fun getItemCount(): Int = rows.size
}
