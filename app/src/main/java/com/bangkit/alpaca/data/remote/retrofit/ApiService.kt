package com.bangkit.alpaca.data.remote.retrofit

import com.bangkit.alpaca.data.remote.response.StoriesResponse
import retrofit2.http.GET

interface ApiService {

    @GET("books")
    suspend fun getAllStoryBook(): StoriesResponse
}