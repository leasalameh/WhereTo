package com.example.whereto

import Place
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProfileActivity : AppCompatActivity() {

    private lateinit var favoritesTab: LinearLayout
    private lateinit var beenTab: LinearLayout
    private lateinit var savedTab: LinearLayout

    private lateinit var favoritesText: TextView
    private lateinit var beenText: TextView
    private lateinit var savedText: TextView

    private lateinit var placesRecyclerView: RecyclerView
    private lateinit var profileAdapter: ProfileAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val logout: ImageButton = findViewById(R.id.logout_button)
        logout.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }


        // Initialize Views
        favoritesTab = findViewById(R.id.favorites_tab)
        beenTab = findViewById(R.id.been_tab)
        savedTab = findViewById(R.id.saved_tab)

        favoritesText = findViewById(R.id.favorites_text)
        beenText = findViewById(R.id.been_text)
        savedText = findViewById(R.id.saved_text)

        placesRecyclerView = findViewById(R.id.places_recycler_view)
        profileAdapter = ProfileAdapter(this, emptyList())

        placesRecyclerView.layoutManager = LinearLayoutManager(this)
        placesRecyclerView.adapter = profileAdapter

        // Set Click Listeners for Tabs
        favoritesTab.setOnClickListener { switchToTab(Tab.FAVORITES) }
        beenTab.setOnClickListener { switchToTab(Tab.BEEN) }
        savedTab.setOnClickListener { switchToTab(Tab.SAVED) }

        // Default Tab
        switchToTab(Tab.FAVORITES)

        // Initialize Footer Buttons (Navbar)
        val footerLayout = findViewById<LinearLayout>(R.id.bottom_navigation)

        // Home button click listener
        footerLayout.findViewById<ImageButton>(R.id.home_button).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Search button click listener
        footerLayout.findViewById<ImageButton>(R.id.search_button).setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        // Profile button click listener (no action needed as we're already on the Profile page)
        footerLayout.findViewById<ImageButton>(R.id.profile_button).setOnClickListener {
            // No action needed as we're already on the Profile page
        }
    }

    enum class Tab {
        FAVORITES, BEEN, SAVED
    }

    private fun switchToTab(tab: Tab) {
        resetTabStyles()

        val places = when (tab) {
            Tab.FAVORITES -> loadPlacesFromStorage("Favorites")
            Tab.BEEN -> loadPlacesFromStorage("Been")
            Tab.SAVED -> loadPlacesFromStorage("Saved")
        }

        when (tab) {
            Tab.FAVORITES -> {
                favoritesText.setTextColor(getColor(android.R.color.black))
                favoritesText.paint.isUnderlineText = true
            }
            Tab.BEEN -> {
                beenText.setTextColor(getColor(android.R.color.black))
                beenText.paint.isUnderlineText = true
            }
            Tab.SAVED -> {
                savedText.setTextColor(getColor(android.R.color.black))
                savedText.paint.isUnderlineText = true
            }
        }

        // Update adapter with the places
        profileAdapter.updatePlaces(places)
    }

    private fun loadPlacesFromStorage(category: String): List<Place> {
        val sharedPref = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)

        return try {
            // Load the JSON String from SharedPreferences
            val json = sharedPref.getString(category, null)
            if (!json.isNullOrEmpty()) {
                // Deserialize the JSON string into a List of Place objects
                val type = object : TypeToken<List<Place>>() {}.type
                Gson().fromJson(json, type)
            } else {
                emptyList()  // Return an empty list if no data
            }
        } catch (e: Exception) {
            emptyList()  // Return empty list in case of error
        }
    }

    private fun resetTabStyles() {
        val defaultColor = getColor(android.R.color.darker_gray)

        favoritesText.setTextColor(defaultColor)
        favoritesText.paint.isUnderlineText = false

        beenText.setTextColor(defaultColor)
        beenText.paint.isUnderlineText = false

        savedText.setTextColor(defaultColor)
        savedText.paint.isUnderlineText = false
    }

    // Save Places to SharedPreferences
    private fun savePlacesToStorage(category: String, places: List<Place>) {
        val sharedPref = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val gson = Gson()

        // Serialize the list of places into a JSON string
        val json = gson.toJson(places)

        // Store the JSON string in SharedPreferences
        editor.putString(category, json)
        editor.apply()
    }

    // Example method to add a place to favorites
    fun addPlaceToFavorites(place: Place) {
        val favorites = loadPlacesFromStorage("Favorites").toMutableList()
        favorites.add(place)
        savePlacesToStorage("Favorites", favorites)
    }
}
