package com.bangkit.alpaca.data.remote.retrofit

import com.bangkit.alpaca.data.remote.response.AudioResponse
import com.bangkit.alpaca.data.remote.response.StoriesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("books")
    suspend fun getAllStoryBook(): StoriesResponse

    @GET("upload")
    suspend fun getAudio(@Query("text") text: String): AudioResponse
}