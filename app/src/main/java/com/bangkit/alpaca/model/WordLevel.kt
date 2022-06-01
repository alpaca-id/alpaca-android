package com.bangkit.alpaca.model

class WordLevel(
    val id: String,
    val level: Int,
    val wordStages: List<WordStage>,
    val isComplete: Boolean
)