package com.example.whereto

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchActivity : ComponentActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchEditText: EditText
    private lateinit var placesAdapter: PlacesAdapter

    // Dummy list of places, now using drawable resource IDs for images
    private val placesList = listOf(
        Place("Joe & The Juice", R.drawable.joe_and_juice),  // Replace with actual drawable resource names
        Place("Em Sherif", R.drawable.em_sherif),
        Place("The Bros", R.drawable.the_bros),
        Place("Barbar", R.drawable.barbar),
        Place("Tom and Mutz", R.drawable.tom_and_mutz),
        Place("Appetito", R.drawable.appetito),
        Place("Swiss Butter", R.drawable.swissbutter),
        Place("Spine", R.drawable.spine),
        Place("SkyBar", R.drawable.skybar),
        Place("Pierre and Friends", R.drawable.pierre_and_friends),
        Place("Mario", R.drawable.mario_e_mario),
        Place("Shawarma Joseph", R.drawable.joseph),
        Place("Abo Sobhi", R.drawable.abosobhi)
        // Add more places with drawable resource IDs
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        // Initialize the RecyclerView and search EditText
        recyclerView = findViewById(R.id.recycler_view)
        searchEditText = findViewById(R.id.search_edit_text)

        // Set up RecyclerView
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        placesAdapter = PlacesAdapter(placesList) { place ->
            // Handle item click (navigate to PlaceDetailsActivity)
        }
        recyclerView.adapter = placesAdapter

        // Add text watcher for filtering
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                placesAdapter.filter(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Initialize footer buttons (if needed)
        val footerLayout = findViewById<LinearLayout>(R.id.bottom_navigation)

        // Home button click listener
        footerLayout.findViewById<ImageButton>(R.id.home_button).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Search button click listener (currently does nothing because we're already on the search page)
        footerLayout.findViewById<ImageButton>(R.id.search_button).setOnClickListener {
            // No action needed as we are already in the search page
        }

        // Profile button click listener
        footerLayout.findViewById<ImageButton>(R.id.profile_button).setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}
