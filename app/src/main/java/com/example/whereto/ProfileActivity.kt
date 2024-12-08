package com.example.whereto

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProfileActivity : AppCompatActivity() {

    private lateinit var favoritesTab: LinearLayout
    private lateinit var beenTab: LinearLayout
    private lateinit var savedTab: LinearLayout

    private lateinit var favoritesText: TextView
    private lateinit var beenText: TextView
    private lateinit var savedText: TextView

    private lateinit var placesRecyclerView: RecyclerView
    private lateinit var profileAdapter: ProfileAdapter

    // Hardcoded places for each tab, using image resources
    private val favoritePlace = Place("Joe and the Juice", R.drawable.joeandthejuice)
    private val beenPlace = Place("Tom&Mutz", R.drawable.tom_and_mutz)
    private val savedPlace = Place("Pierre and Friends", R.drawable.pierre_and_friends)

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
    }

    enum class Tab {
        FAVORITES, BEEN, SAVED
    }

    private fun switchToTab(tab: Tab) {
        resetTabStyles()

        // Hardcoded places for each tab
        val place = when (tab) {
            Tab.FAVORITES -> favoritePlace
            Tab.BEEN -> beenPlace
            Tab.SAVED -> savedPlace
        }

        // Highlight selected tab
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

        // Update adapter with the single hardcoded place
        profileAdapter.updatePlaces(listOf(place))  // Send a single place for the selected tab
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
}
