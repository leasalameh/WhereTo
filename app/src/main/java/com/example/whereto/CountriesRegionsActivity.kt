package com.example.whereto

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whereto.ChatActivity

class CountriesRegionsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var titleText: TextView
    private lateinit var searchEditText: EditText
    private lateinit var search: ImageButton
    private lateinit var home: ImageButton
    private lateinit var backArrow: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regionspage)  // Main layout with included footer

        backArrow = findViewById(R.id.back_button)
        backArrow.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Initialize your UI components
        recyclerView = findViewById(R.id.recycler_view)
        titleText = findViewById(R.id.region_label)
        searchEditText = findViewById(R.id.search_edit_text)

        // Initialize RecyclerView
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        // Setup initial region view
        setupRegions()

        // Adding TextWatcher for search functionality
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                (recyclerView.adapter as? CountriesAdapter)?.filter(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Initialize footer buttons
        val footerLayout = findViewById<LinearLayout>(R.id.bottom_navigation)  // Adjust based on your layout

        // Initialize footer buttons
        search = footerLayout.findViewById(R.id.search_button)
        home = footerLayout.findViewById(R.id.home_button)

        // Set OnClickListeners for the footer buttons
        search.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
        home.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRegions() {
        val regions = listOf(
            CountriesAdapter.RegionItem("Middle East", R.drawable.region_middle_east),
            CountriesAdapter.RegionItem("Europe", R.drawable.region_europe)
        )
        recyclerView.adapter = CountriesAdapter(regions) { region ->
            showCountries(region.name)
        }
        searchEditText.visibility = View.GONE
    }

    private fun showCountries(region: String) {
        val countries = when (region) {
            "Middle East" -> listOf(
                CountriesAdapter.RegionItem("Lebanon", R.drawable.country_lebanon),  // Lebanon is clickable
                CountriesAdapter.RegionItem("Dubai", R.drawable.country_dubai, true),
                CountriesAdapter.RegionItem("Qatar", R.drawable.country_qatar, true),
                CountriesAdapter.RegionItem("Egypt", R.drawable.country_egypt, true)
            )
            "Europe" -> listOf(
                CountriesAdapter.RegionItem("Paris", R.drawable.paris_comingsoon),
                CountriesAdapter.RegionItem("Barcelona", R.drawable.country_barcelona, true),
                CountriesAdapter.RegionItem("Madrid", R.drawable.country_madrid, true),
                CountriesAdapter.RegionItem("Rome", R.drawable.country_rome, true)
            )
            else -> emptyList() // Ensure this is properly formatted as a default case
        }
        recyclerView.adapter = CountriesAdapter(countries) { regionItem ->
            // Only Lebanon takes you to MainActivity
            if (regionItem.name == "Lebanon") {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        searchEditText.visibility = View.VISIBLE
        titleText.text = getString(R.string.label_country)
    }
}
