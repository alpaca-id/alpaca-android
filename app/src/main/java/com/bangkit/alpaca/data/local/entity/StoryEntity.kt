package com.bangkit.alpaca.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bangkit.alpaca.data.remote.response.StoryItem
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "story")
class StoryEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val body: String,
    val coverPath: String,
    val authorName: String,
    val createdAt: String,
) : Parcelable {
    companion object {
        fun StoryItem.toStory(): StoryEntity {
            return StoryEntity(
                id = id,
                title = title,
                body = story,
                coverPath = image,
                authorName = author,
                createdAt = createdAt
            )
        }
    }
}
