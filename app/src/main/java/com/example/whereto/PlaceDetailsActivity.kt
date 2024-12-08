package com.example.whereto

import Place
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_details)

        // Get the placeId passed through the intent
        val placeId = intent.getStringExtra("placeId")

        // Check if the placeId is not null and fetch the place details
        if (placeId != null) {
            fetchPlaceDetails(placeId)
        } else {
            // Handle case where placeId is not passed (e.g., show error or fallback)
            Toast.makeText(this, "Place ID not found", Toast.LENGTH_SHORT).show()
        }

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

    private fun fetchPlaceDetails(placeId: String) {
        // Create an instance of your ApiService
        val apiService = ApiClient.getClient().create(ApiService::class.java)

        // Make the API call to fetch details of the place
        val call = apiService.getPlaceDetails(placeId)

        call.enqueue(object : Callback<Place> {
            override fun onResponse(call: Call<Place>, response: Response<Place>) {
                if (response.isSuccessful) {
                    val place = response.body()
                    if (place != null) {
                        // Log the API response to check the data
                        Log.d("API Response", "Fetched Place Details: $place")

                        // Now call the method to update the UI with the fetched data
                        updateUI(place)
                    } else {
                        Toast.makeText(this@PlaceDetailsActivity, "Place not found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("API Error", "Error fetching data: ${response.message()}")
                    Toast.makeText(this@PlaceDetailsActivity, "Error fetching data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Place>, t: Throwable) {
                Log.e("API Failure", "Failed to fetch place details: ${t.message}")
                Toast.makeText(this@PlaceDetailsActivity, "Failed to fetch data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateUI(place: Place) {
        // Assuming you have set up your UI elements (TextViews, ImageViews, etc.)
        val placeTitle: TextView = findViewById(R.id.place_title)
        val placeLocation: TextView = findViewById(R.id.place_location)
        val placeDescription: TextView = findViewById(R.id.place_description)
        val placeImage: ImageView = findViewById(R.id.place_image)
        val placeTagsLayout: LinearLayout = findViewById(R.id.place_tags)

        // Populate the UI with the data from the API response
        placeTitle.text = place.name
        placeLocation.text = place.location
        placeDescription.text = place.description
        Glide.with(this)  // Glide for image loading
            .load(place.imageUrl)
            .into(placeImage)

        // Populate tags dynamically
        for (tag in place.tags) {
            val tagTextView = TextView(this)
            tagTextView.text = tag
            tagTextView.setTextColor(Color.BLACK)
            tagTextView.setBackgroundResource(R.drawable.tag_background)
            tagTextView.setPadding(15, 9, 15, 9)

            // Add margin to each tag
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(10, 0, 10, 0) // Set the margins (left, top, right, bottom)

            // Apply the layout parameters to the tag
            tagTextView.layoutParams = params

            // Add the tag to the tags container
            placeTagsLayout.addView(tagTextView)
        }

        // Set other fields like price range, rating, opening hours, etc.
        val priceRange: TextView = findViewById(R.id.place_price_range)
        priceRange.text = place.priceRange

        val ratingBar: RatingBar = findViewById(R.id.place_rating)
        ratingBar.rating = place.rating.toFloat()

        val openingHours: TextView = findViewById(R.id.opening_hours_text)
        openingHours.text = place.openingHours.joinToString("\n")

        val locationAddress: TextView = findViewById(R.id.location_address)
        locationAddress.text = place.locationaddress

        val contactInfo: TextView = findViewById(R.id.contact_info)
        contactInfo.text = place.contactInfo

        val instaPage: TextView = findViewById(R.id.insta_info)
        instaPage.text = place.instapage
    }
}