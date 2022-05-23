package com.bangkit.alpaca.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stories")
data class StoryEntity(

    @PrimaryKey
    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "body")
    val body: String,

    @ColumnInfo(name = "cover_path")
    val coverPath: String?,

    @ColumnInfo(name = "author_name")
    val authorName: String?,

    @ColumnInfo(name = "created_at")
    val createdAt: Long

)