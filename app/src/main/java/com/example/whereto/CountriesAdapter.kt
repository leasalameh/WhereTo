package com.example.whereto

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CountriesAdapter(private var items: List<RegionItem>, private val itemClick: (RegionItem) -> Unit)
    : RecyclerView.Adapter<CountriesAdapter.ViewHolder>() {

    var filteredItems: List<RegionItem> = items

    data class RegionItem(val name: String, val imageRes: Int, val comingSoon: Boolean = false)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutId = if (filteredItems[0].comingSoon) R.layout.item_country else R.layout.item_region
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filteredItems[position])
    }

    override fun getItemCount() = filteredItems.size

    fun filter(query: String) {
        filteredItems = if (query.isEmpty()) {
            items
        } else {
            items.filter { it.name.contains(query, ignoreCase = true) }
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageCountry) ?: itemView.findViewById(R.id.imageRegion)
        private val textView: TextView = itemView.findViewById(R.id.textCountry) ?: itemView.findViewById(R.id.textRegion)

        fun bind(item: RegionItem) {
            imageView.setImageResource(item.imageRes)
            textView.text = item.name
            itemView.setOnClickListener { itemClick(item) }
        }
    }
}
