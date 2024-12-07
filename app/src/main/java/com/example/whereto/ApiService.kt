package com.example.whereto

import Place
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    // POST request for sending the user query to the chatbot
    @POST("api/chatbot")
    fun sendQuery(@Body query: Query): Call<ResponseBody>

    // GET request to fetch all places
    @GET("api/places")
    fun getPlaces(): Call<List<Place>>
}