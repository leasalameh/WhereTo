package com.example.whereto

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlacesAdapter(private var items: List<Place>, private val itemClick: (Place) -> Unit) :
    RecyclerView.Adapter<PlacesAdapter.ViewHolder>() {

    var filteredItems: List<Place> = items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_searchplace, parent, false)
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
        private val imageView: ImageView = itemView.findViewById(R.id.imagePlace)
        private val textView: TextView = itemView.findViewById(R.id.textPlace)

        fun bind(place: Place) {
            textView.text = place.name
            imageView.setImageResource(place.imageRes)  // Load the image from drawable resources
            itemView.setOnClickListener { itemClick(place) }
        }
    }
}

data class Place(val name: String, val imageRes: Int)  // imageRes will be a reference to the drawable resource ID
