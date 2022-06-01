package com.bangkit.alpaca.model

class Level(
    val id: String,
    val level: Int,
    val stages: List<Stage>,
    val isComplete: Boolean
)