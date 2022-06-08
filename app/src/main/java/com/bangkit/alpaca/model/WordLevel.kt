package com.bangkit.alpaca.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WordLevel(
    val id: String,
    val level: Int,
    val wordStages: List<WordStage>,
    var isComplete: Boolean
):Parcelable