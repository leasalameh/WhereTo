package com.example.whereto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatapp.ChatActivity
import com.example.whereto.ui.theme.WhereToTheme

class PlaceDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_details)

        // Retrieve the button ID passed in the intent
        val buttonId = intent.getIntExtra("image_id", -1)  // Default to -1 if no data is passed

        // Log the buttonId to check if it's correct
        Log.d("PlaceDetailsActivity", "Received buttonId: $buttonId")

        // Use the ID to show relevant data or perform actions
        when (buttonId) {
            R.id.JJ -> {
                // Handle Joe and Juice logic here
                showDetails("Joe and the Juice")
            }
            R.id.emSherif -> {
                // Handle Em Sherif logic here
                showDetails("Em Sherif")
            }
            R.id.theBros -> {
                // Handle The Bros logic here
                showDetails("The Bros")
            }
            // Handle other IDs similarly
            else -> {
                // Handle unknown ID or show default
                Toast.makeText(this, "Unknown place", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to show details based on place
    private fun showDetails(placeName: String) {
        // Display place details, e.g., set text, load image, etc.
        Toast.makeText(this, "Showing details for $placeName", Toast.LENGTH_SHORT).show()
    }
}
