package com.example.whereto

import Place
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.content.Intent
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaceDetailsActivity : AppCompatActivity() {

    private lateinit var placeId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_details)

        // Get the placeId passed through the intent
        placeId = intent.getStringExtra("placeId") ?: ""

        if (placeId.isNotEmpty()) {
            fetchPlaceDetails(placeId)
        } else {
            Toast.makeText(this, "Place ID not found", Toast.LENGTH_SHORT).show()
        }

        // Initialize icons and set click listeners
        val favoriteIcon: ImageView = findViewById(R.id.heart_icon)
        val beenIcon: ImageView = findViewById(R.id.check_icon)
        val saveIcon: ImageView = findViewById(R.id.save_icon)

        favoriteIcon.setOnClickListener { addToCategory("Favorites") }
        beenIcon.setOnClickListener { addToCategory("Been") }
        saveIcon.setOnClickListener { addToCategory("Saved") }
    }

    // Store the place in SharedPreferences
    private fun addToCategory(category: String) {
        if (placeId.isEmpty()) {
            Toast.makeText(this, "Place not loaded yet", Toast.LENGTH_SHORT).show()
            return
        }

        // Use SharedPreferences
        val sharedPref = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        // Retrieve the current set of places
        val currentPlaces = sharedPref.getStringSet(category, mutableSetOf()) ?: mutableSetOf()

        if (currentPlaces.contains(placeId)) {
            Toast.makeText(this, "Already added to $category", Toast.LENGTH_SHORT).show()
        } else {
            currentPlaces.add(placeId)
            editor.putStringSet(category, currentPlaces)
            editor.apply()
            Toast.makeText(this, "Added to $category", Toast.LENGTH_SHORT).show()
            // Find the buttons by their IDs in the main layout
            val searchButton: ImageButton = findViewById(R.id.search_button)
            val homeButton: ImageButton = findViewById(R.id.home_button)
            val backArrow: ImageButton = findViewById(R.id.back_arrow)

            searchButton.setOnClickListener {
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
            }

            homeButton.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

            backArrow.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

    }

        private fun fetchPlaceDetails(placeId: String) {
            val apiService = ApiClient.getClient().create(ApiService::class.java)
            val call = apiService.getPlaceDetails(placeId)

            call.enqueue(object : Callback<Place> {
                override fun onResponse(call: Call<Place>, response: Response<Place>) {
                    if (response.isSuccessful) {
                        val place = response.body()
                        if (place != null) {
                            Log.d("API Response", "Fetched Place Details: $place")
                            updateUI(place)
                        } else {
                            Toast.makeText(
                                this@PlaceDetailsActivity,
                                "Place not found",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Log.e("API Error", "Error fetching data: ${response.message()}")
                        Toast.makeText(
                            this@PlaceDetailsActivity,
                            "Error fetching data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Place>, t: Throwable) {
                    Log.e("API Failure", "Failed to fetch place details: ${t.message}")
                    Toast.makeText(
                        this@PlaceDetailsActivity,
                        "Failed to fetch data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }

        private fun updateUI(place: Place) {
            findViewById<TextView>(R.id.place_title).text = place.name
            findViewById<TextView>(R.id.place_location).text = place.location
            findViewById<TextView>(R.id.place_description).text = place.description

            Glide.with(this).load(place.imageUrl).into(findViewById(R.id.place_image))

            findViewById<TextView>(R.id.place_price_range).text = place.priceRange
            findViewById<RatingBar>(R.id.place_rating).rating = place.rating.toFloat()
            findViewById<TextView>(R.id.opening_hours_text).text =
                place.openingHours.joinToString("\n")
            findViewById<TextView>(R.id.location_address).text = place.locationaddress
            findViewById<TextView>(R.id.contact_info).text = place.contactInfo
            findViewById<TextView>(R.id.insta_info).text = place.instapage

            // Update tags dynamically
            val placeTagsLayout: LinearLayout = findViewById(R.id.place_tags)
            placeTagsLayout.removeAllViews()

            for (tag in place.tags) {
                val tagTextView = TextView(this)
                tagTextView.text = tag
                tagTextView.setTextColor(Color.BLACK)
                tagTextView.setBackgroundResource(R.drawable.tag_background)
                tagTextView.setPadding(15, 9, 15, 9)

                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(10, 0, 10, 0)

                tagTextView.layoutParams = params
                placeTagsLayout.addView(tagTextView)
            }
        }
    }