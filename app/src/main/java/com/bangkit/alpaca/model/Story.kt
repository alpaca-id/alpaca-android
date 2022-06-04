package com.bangkit.alpaca.model

import android.os.Parcelable
import android.util.Log
import com.bangkit.alpaca.data.local.entity.StoryEntity
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.ktx.Firebase
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Story(
    val id: String?,
    val title: String,
    val body: String,
    val coverPath: String?,
    val authorName: String?,
    val createdAt: Long
) : Parcelable {
    companion object {
        fun DocumentSnapshot.toStory(): Story? {
            return try {
                val id = id
                val title = getString("title").toString()
                val body = getString("body").toString()
                val authorName = getString("authorName")
                val coverPath = getString("coverPath")
                val createdAt = getLong("createdAt") ?: Calendar.getInstance().timeInMillis

                Story(id, title, body, coverPath, authorName, createdAt)
            } catch (e: Exception) {
                Firebase.crashlytics.apply {
                    log("Error converting DocumentSnapshot to Story")
                    setCustomKey("user_id", id)
                    recordException(e)
                }

                Log.e(TAG, "toStory: ${e.message}")
                null
            }
        }

        fun StoryEntity.toStory(): Story {
            return Story(
                id = id,
                title = title,
                body = body,
                coverPath = coverPath,
                authorName = authorName,
                createdAt = createdAt.toLong()
            )
        }

        private const val TAG = "Story"
    }
}