package com.bangkit.alpaca.model

import android.util.Log
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.ktx.Firebase
import java.util.*

data class Story(
    val id: String?,
    val title: String,
    val body: String,
    val coverPath: String?,
    val authorName: String?,
    val createdAt: Long
) {
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

        private const val TAG = "Story"
    }
}