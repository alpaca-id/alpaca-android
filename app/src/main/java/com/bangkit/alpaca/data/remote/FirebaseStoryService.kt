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
     * Fetch all user's saved stories (from scan)
     *
     * @return Flow
     */
    fun getUserStoriesScan(): Flow<List<Story>?> = callbackFlow {
        val email = Firebase.auth.currentUser?.email
        val listener = Firebase.firestore.collection("users/$email/stories-scan")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Firebase.crashlytics.apply {
                        log("Error get user stories-scan")
                        recordException(error)
                    }

                    cancel(message = "Error get user stories-scan", cause = error)
                    return@addSnapshotListener
                }

                val stories =
                    value?.documents?.mapNotNull { it.toStory() }?.sortedBy { it.createdAt }

                trySend(stories)
            }

        awaitClose {
            Log.d(TAG, "getUserStoriesScan: Cancelling stories listener")
            listener.remove()
        }
    }

}