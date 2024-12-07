package com.example.whereto

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://10.0.2.2:3000/"  // Replace with your actual backend URL if hosted on a server
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)  // Backend base URL
                .addConverterFactory(GsonConverterFactory.create())  // Gson converter for JSON responses
                .build()
        }
        return retrofit!!
    }
}