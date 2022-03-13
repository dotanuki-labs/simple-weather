package io.dotanuki.demos.weather.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import iio.dotanuki.demos.weather.R
import io.dotanuki.demos.weather.navigation.WeatherNavigator
import io.dotanuki.demos.weather.presentation.WeatherRow

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
                val contentTextView = itemView.findViewById<TextView>(R.id.textWeatherContent)
                contentTextView.text = row.title
                setOnClickListener { navigator.toDetails(row.title) }
                // itemView.findViewById<ImageView>(R.id.imageWeatherPreview).load()
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
