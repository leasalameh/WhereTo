package com.example.whereto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.whereto.ChatActivity
import com.example.whereto.ui.theme.WhereToTheme
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

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
            val buttonId = view.id
            Log.d("MainActivity", "Clicked buttonId: $buttonId")  // Log the clicked buttonId

            val placeId = when (buttonId) {
                R.id.JJ -> "675483161c3876119924977b" // Joe & The Juice
                R.id.emSherif -> "675483161c3876119924977c" // Em Sherif
                R.id.theBros -> "675483161c3876119924977d" // The Bros
                R.id.appetito -> "675483161c3876119924977f" // Appetito
                R.id.tandm -> "675483161c3876119924977e" // Tom & Mutz
                R.id.alegna -> "675483161c38761199249780" // Alegna
                R.id.spine -> "675483161c38761199249783" // Spine
                R.id.skybar -> "675483161c38761199249782" // SkyBar
                R.id.pierreAndFriends -> "675483161c38761199249781" // Pierre & Friends
                R.id.barbar -> "675483161c38761199249784" // Barbar
                R.id.josephShawarma -> "675483161c38761199249785" // Shawarma Joseph
                R.id.aboSobhi -> "675483161c38761199249786" // Abou Sobhi
                else -> "" // Default case if ID is unknown
            }

            // Use the ID to pass data to PlaceDetailsActivity
            val intent = Intent(this, PlaceDetailsActivity::class.java)
            intent.putExtra("placeId", placeId)  // Passing the correct placeId
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