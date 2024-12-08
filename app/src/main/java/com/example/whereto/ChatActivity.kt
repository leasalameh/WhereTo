package com.example.whereto

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.widget.ImageView
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChatActivity : AppCompatActivity() {

    private lateinit var messageInput: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var messageContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbot)  // Make sure the layout file is correct

        val backArrow: ImageButton = findViewById(R.id.back_arrow)

        backArrow.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Find the buttons by their IDs in the main layout
        val searchButton: ImageButton = findViewById(R.id.search_button)
        val homeButton: ImageButton = findViewById(R.id.home_button)
        val profile: ImageButton = findViewById(R.id.profile_button)

        searchButton.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        profile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }


        // Initialize views
        messageInput = findViewById(R.id.message_input)
        sendButton = findViewById(R.id.send_button)
        messageContainer = findViewById(R.id.message_container)

        // Display the default AI message when the chat starts
        displayMessage("WhereTo Today?", isAiMessage = true)

        // Set up the Send button click listener
        sendButton.setOnClickListener {
            val messageText = messageInput.text.toString()
            if (messageText.isNotEmpty()) {
                sendMessage(messageText) // Send the user message
                messageInput.text.clear() // Clear the input after sending
            }
        }
    }

    // Function to display messages in the chat
    private fun displayMessage(message: String, isAiMessage: Boolean) {
        val containerLayout = LinearLayout(this)
        containerLayout.orientation = LinearLayout.HORIZONTAL

        // Align messages based on sender (AI or User)
        if (isAiMessage) {
            containerLayout.gravity = Gravity.START
        } else {
            containerLayout.gravity = Gravity.END
        }

        val textView = TextView(this)
        textView.text = message

        // Apply different background based on the sender
        if (isAiMessage) {
            textView.setBackgroundResource(R.drawable.rounded_left_bubble)
        } else {
            textView.setBackgroundResource(R.drawable.rounded_right_bubble)
        }
        textView.setTextColor(Color.BLACK)

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        textView.layoutParams = params

        containerLayout.addView(textView)

        if (isAiMessage) {
            val avatarImageView = ImageView(this)
            avatarImageView.setImageResource(R.drawable.ic_ai_avatar)

            val avatarParams = LinearLayout.LayoutParams(48, 48)
            avatarParams.marginEnd = 8
            avatarImageView.layoutParams = avatarParams
            containerLayout.addView(avatarImageView, 0)
        }

        messageContainer.addView(containerLayout)
    }

    // Function to handle sending a message and calling the backend API
    private fun sendMessage(message: String) {
        displayMessage(message, isAiMessage = false)

        // Call the backend API to get a response
        sendQueryToApi(message) { response ->
            displayMessage(response, isAiMessage = true)
        }
    }

    // Function to send the query to the backend API using Retrofit
    fun sendQueryToApi(query: String, onResponse: (String) -> Unit) {
        // Set the Retrofit client base URL to your API URL
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")  // Replace with your backend URL if needed
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create an instance of your API service
        val apiService = retrofit.create(ApiService::class.java)

        // Prepare the request body (send query as JSON)
        val queryModel = Query(query)

        // Make the API call
        val call = apiService.sendQuery(queryModel)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    onResponse(response.body()?.string() ?: "No response from AI")
                } else {
                    onResponse("Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onResponse("Failure: ${t.message}")
            }
        })
    }
}