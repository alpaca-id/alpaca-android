package com.bangkit.alpaca.data.remote

import android.util.Log
import com.bangkit.alpaca.model.Story
import com.bangkit.alpaca.model.Story.Companion.toStory
import com.google.firebase.auth.ktx.auth
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
object FirebaseStoryService {
    private const val TAG = "FirebaseStoryService"

    /**
     * Save new story to the database
     *
     * @param story Story
     */
    fun saveNewStory(userId: String?, story: Story) {
        try {
            Firebase.firestore.collection("users/$userId/stories-scan").add(story)
        } catch (e: Exception) {
            Firebase.crashlytics.apply {
                log("Error save new story")
                recordException(e)
            }

            Log.e(TAG, "saveNewStory: ${e.message}")
        }
    }

    /**
     * Fetch all user's saved stories (from scan)
     *
     * @return Flow
     */
    fun getUserStoriesScan(id: String?): Flow<List<Story>?> = callbackFlow {
        val listener = Firebase.firestore.collection("users/$id/stories-scan")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Firebase.crashlytics.apply {
                        log("Error get user stories-scan")
                        recordException(error)
                    }

                    cancel(message = "Error get user stories-scan", cause = error)
                    return@addSnapshotListener
                }

                val stories = value?.documents
                    ?.mapNotNull { it.toStory() }
                    ?.sortedByDescending { it.createdAt }

                trySend(stories)
            }

        awaitClose {
            Log.d(TAG, "getUserStoriesScan: Cancelling stories listener")
            listener.remove()
        }
    }

    /**
     * Get user's document id based on the user's email address
     */
    fun getUserDocumentID(): Flow<String?> = callbackFlow {
        val email = Firebase.auth.currentUser?.email
        val listener = Firebase.firestore.collection("users")
            .whereEqualTo("email", email)
            .limit(1)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Firebase.crashlytics.apply {
                        log("Error get documents with related email")
                        recordException(error)
                    }

                    cancel(message = "Error get documents with related email", cause = error)
                    return@addSnapshotListener
                }

                val document = value?.documents?.first()
                trySend(document?.id)
            }

        awaitClose {
            Log.d(TAG, "getUserDocumentID: Cancelling document id listener")
            listener.remove()
        }

    }

    fun getLibraryStory(): Flow<List<Story>?> = callbackFlow {
        val listener = Firebase.firestore.collection("stories")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Firebase.crashlytics.apply {
                        log("Error get user stories-scan")
                        recordException(error)
                    }

                    cancel(message = "Error get user stories-scan", cause = error)
                    return@addSnapshotListener
                }

                val stories = value?.documents
                    ?.mapNotNull { it.toStory() }
                    ?.sortedByDescending { it.createdAt }

                trySend(stories)
            }
        awaitClose {
            Log.d(TAG, "getUserDocumentID: Cancelling document id listener")
            listener.remove()
        }
    }

}