package com.bangkit.alpaca.data.remote.response

import com.google.gson.annotations.SerializedName

class AudioResponse(

    @field:SerializedName("audio")
    val audioUrl: String = ""
)