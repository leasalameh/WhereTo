package com.example.chatapp

import android.os.Bundle
import android.view.Gravity
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.whereto.R
import android.graphics.Color

class ChatActivity : AppCompatActivity() {

    private lateinit var messageInput: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var messageContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbot)  // Make sure the layout file is correct

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

    // Function to display messages
    private fun displayMessage(message: String, isAiMessage: Boolean) {
        // Create a container layout for the message (either user or AI)
        val containerLayout = LinearLayout(this)
        containerLayout.orientation = LinearLayout.HORIZONTAL // Horizontal layout for the avatar and message

        // Apply gravity to align messages to left (AI) or right (User)
        if (isAiMessage) {
            containerLayout.gravity = Gravity.START // Align AI messages to the left
        } else {
            containerLayout.gravity = Gravity.END   // Align User messages to the right
        }

        // Create the TextView for the message
        val textView = TextView(this)
        textView.text = message

        // Apply styles based on whether it's an AI message or a user message
        if (isAiMessage) {
            textView.setBackgroundResource(R.drawable.rounded_left_bubble) // Gray background for AI message
        } else {
            textView.setBackgroundResource(R.drawable.rounded_right_bubble) // Blue background for user message
        }

        textView.setTextColor(Color.BLACK)

        // Set the layout parameters for the TextView
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,  // Set width based on content
            LinearLayout.LayoutParams.WRAP_CONTENT   // Set height based on content
        )
        textView.layoutParams = params

        // Add the TextView to the container layout
        containerLayout.addView(textView)

        // If this is an AI message, add the avatar icon before the message
        if (isAiMessage) {
            // Create ImageView for the AI avatar
            val avatarImageView = ImageView(this)
            avatarImageView.setImageResource(R.drawable.ic_ai_avatar) // Set the avatar icon

            // Set the image size (optional, can adjust based on your requirements)
            val avatarParams = LinearLayout.LayoutParams(
                48, // width of the avatar
                48  // height of the avatar
            )
            avatarParams.marginEnd = 8 // Add space between the avatar and the message
            avatarImageView.layoutParams = avatarParams

            // Add the avatar ImageView before the message TextView in the container layout
            containerLayout.addView(avatarImageView, 0)  // Add the avatar at index 0 (before the message)
        }

        // Add the container layout to the message container
        messageContainer.addView(containerLayout)
    }

    // Function to handle sending a message
    private fun sendMessage(message: String) {
        // Display the user message in the chat (right-aligned)
        displayMessage(message, isAiMessage = false)

        // Optionally, show an AI response
        displayMessage("Here's a suggestion: Try a cozy cafe nearby!", isAiMessage = true)
    }
}
