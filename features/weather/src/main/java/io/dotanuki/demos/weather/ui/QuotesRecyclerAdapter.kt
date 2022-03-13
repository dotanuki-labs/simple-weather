package io.dotanuki.demos.weather.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.dotanuki.bootstrap.listing.R
import io.dotanuki.demos.weather.domain.Quote

class QuotesRecyclerAdapter(private val quotes: List<Quote>) :
    RecyclerView.Adapter<QuotesRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(quote: Quote) {
            itemView.run {

                val quoteAuthorText = itemView.findViewById<TextView>(R.id.textQuoteAuthor)
                val quoteContentText = itemView.findViewById<TextView>(R.id.textQuoteContent)

                quoteAuthorText.text = quote.author
                quoteContentText.text = quote.content
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_quote, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(quotes[position])

    override fun getItemCount(): Int = quotes.size
}
