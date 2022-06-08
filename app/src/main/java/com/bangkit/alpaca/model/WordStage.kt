package com.bangkit.alpaca.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WordStage(
    val id: String,
    val stage: Int,
    val word: String,
    var isComplete: Boolean
) : Parcelable