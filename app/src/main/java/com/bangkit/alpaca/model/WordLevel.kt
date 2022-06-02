package com.bangkit.alpaca.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class WordLevel(
    val id: String,
    val level: Int,
    val wordStages: List<WordStage>,
    val isComplete: Boolean
):Parcelable