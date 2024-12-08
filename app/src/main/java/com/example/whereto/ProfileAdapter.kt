package com.example.whereto

import Place
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProfileAdapter(private val context: Context, private var places: List<Place>) : RecyclerView.Adapter<ProfileAdapter.PlaceViewHolder>() {

    // ViewHolder to hold each item view
    inner class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.place_name)
        val locationTextView: TextView = itemView.findViewById(R.id.place_location)
    }

    // Create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_place, parent, false)
        return PlaceViewHolder(itemView)
    }

    // Bind data to each view
    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val place = places[position]
        holder.nameTextView.text = place.name
        holder.locationTextView.text = place.location
    }

    // Return the size of the list
    override fun getItemCount(): Int {
        return places.size
    }

    // Update the list of places
    fun updatePlaces(newPlaces: List<Place>) {
        places = newPlaces
        notifyDataSetChanged()  // Notify the adapter that the data has changed
    }
}
