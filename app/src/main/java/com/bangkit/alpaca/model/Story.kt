package com.bangkit.alpaca.model


data class Story(
    val title: String,
    val body: String,
    val coverPath: String?,
    val authorName: String?,
    val createdAt: Long
)