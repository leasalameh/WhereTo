package com.example.whereto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatapp.ChatActivity
import com.example.whereto.ui.theme.WhereToTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)  // Ensure this is the correct layout file

        // Find the buttons by their IDs in the main layout
        val changeButton: ImageButton = findViewById(R.id.change_button)
        val searchButton: ImageButton = findViewById(R.id.search_button)
        val chatButton: ImageButton = findViewById(R.id.chat_button)


        // Set an OnClickListener for the change button
        changeButton.setOnClickListener {
            val intent = Intent(this, CountriesRegionsActivity::class.java)
            intent.putExtra("key_name", "value")  // Optional extra data
            startActivity(intent)
        }

        // Set OnClickListener for other buttons
        chatButton.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }

        searchButton.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        val joeAndJuice: ImageButton = findViewById(R.id.JJ)
        val emSherif: ImageButton = findViewById(R.id.emSherif)
        val theBros: ImageButton = findViewById(R.id.theBros)
        val tandm: ImageButton = findViewById(R.id.tandm)
        val appetito: ImageButton = findViewById(R.id.appetito)
        val alegna: ImageButton = findViewById(R.id.alegna)
        val pierreAndFriends: ImageButton = findViewById(R.id.pierreAndFriends)
        val skybar: ImageButton = findViewById(R.id.skybar)
        val spine: ImageButton = findViewById(R.id.spine)
        val barbar: ImageButton = findViewById(R.id.barbar)
        val josephShawarma: ImageButton = findViewById(R.id.josephShawarma)
        val aboSobhi: ImageButton = findViewById(R.id.aboSobhi)


        val clickListener = View.OnClickListener { view ->
            // Retrieve the ID of the clicked button
            val buttonId = view.id
            Log.d("MainActivity", "Clicked buttonId: $buttonId")  // Log the clicked buttonId

            // Use the ID  to pass data to the target activity
            val intent = Intent(this, PlaceDetailsActivity::class.java)
            intent.putExtra("image_id", buttonId)  //passing the id to know which data to get

            // Start the activity
            startActivity(intent)
        }

        // Set the same OnClickListener for all ImageButtons
        joeAndJuice.setOnClickListener(clickListener)
        emSherif.setOnClickListener(clickListener)
        theBros.setOnClickListener(clickListener)
        tandm.setOnClickListener(clickListener)
        appetito.setOnClickListener(clickListener)
        alegna.setOnClickListener(clickListener)
        pierreAndFriends.setOnClickListener(clickListener)
        skybar.setOnClickListener(clickListener)
        spine.setOnClickListener(clickListener)
        barbar.setOnClickListener(clickListener)
        josephShawarma.setOnClickListener(clickListener)
        aboSobhi.setOnClickListener(clickListener)

    }

}

