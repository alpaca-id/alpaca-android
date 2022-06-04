package com.bangkit.alpaca.data.remote.response

import com.google.gson.annotations.SerializedName


data class StoriesResponse(

    @field:SerializedName("data")
    val data: Data,

    @field:SerializedName("status")
    val status: String
)


data class Data(

    @field:SerializedName("books")
    val stories: List<StoryItem>
)

data class StoryItem(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("story")
    val story: String,

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("author")
    val author: String,

    @field:SerializedName("createdAt")
    val createdAt: String
)
