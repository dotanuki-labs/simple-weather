package io.dotanuki.demos.weather.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import iio.dotanuki.demos.weather.R
import io.dotanuki.demos.weather.presentation.ForecastPage
import io.dotanuki.demos.weather.presentation.ForecastPageContent

class ForecastPagerAdapter(private val pages: List<ForecastPage>) :
    RecyclerView.Adapter<ForecastPagerAdapter.ViewHolder>() {

    class ViewHolder(private val root: View) : RecyclerView.ViewHolder(root) {
        fun bind(page: ForecastPage) {
            when (page.content) {
                ForecastPageContent.Empty -> showEmptyContent()
                is ForecastPageContent.Formatted -> showFormatted(page.content)
            }
        }

        private fun showFormatted(content: ForecastPageContent.Formatted) {
            root.findViewById<TextView>(R.id.textEmptyContent).visibility = View.GONE
            val recyclerView = root.findViewById<RecyclerView>(R.id.contentRecyclerView)
            recyclerView.layoutManager = LinearLayoutManager(root.context)
            recyclerView.adapter = ForecastRecyclerAdapter(content.rows)
        }

        private fun showEmptyContent() {
            root.findViewById<TextView>(R.id.textEmptyContent).visibility = View.VISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.view_forecast, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pages[position])
    }

    override fun getItemCount(): Int = pages.size
}
