package com.example.whereto

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.activity.compose.setContent
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbot)

        // Get the UI elements using their IDs correctly
        val queryInput = findViewById<EditText>(R.id.message_input) // Correct ID for EditText
        val submitButton = findViewById<Button>(R.id.send_button) // Correct ID for Button
        val responseTextView = findViewById<TextView>(R.id.responseText) // Correct ID for TextView

        // Set up the button click listener
        submitButton.setOnClickListener {
            val query = queryInput.text.toString()
            sendQueryToApi(query) { response ->
                // Display the response
                responseTextView.text = response
                Toast.makeText(applicationContext, "Response: $response", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun sendQueryToApi(query: String, onResponse: (String) -> Unit) {
        val apiService = ApiClient.getClient().create(ApiService::class.java)

        val queryModel = Query(query)
        val call = apiService.sendQuery(queryModel)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    onResponse(response.body()?.string() ?: "No response")
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