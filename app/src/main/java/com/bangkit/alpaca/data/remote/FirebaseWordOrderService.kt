package com.bangkit.alpaca.data.remote

import android.util.Log
import com.bangkit.alpaca.model.WordLevel
import com.bangkit.alpaca.model.WordStage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

@ExperimentalCoroutinesApi
object FirebaseWordOrderService {
    fun getWordOrderDataSource(): Flow<List<WordLevel>> = callbackFlow {
        val listener = Firebase.firestore.collection("games/word-order/levelling")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Firebase.crashlytics.apply {
                        log("Error when get the word order data")
                        recordException(error)
                    }

                    cancel(message = "Error when get the word order data", cause = error)
                    return@addSnapshotListener
                }

                val wordLevel = value?.documents?.mapNotNull { docs ->
                    val level = docs["level"].toString().toInt()
                    val wordStages = mutableListOf<WordStage>()

                    docs.reference.collection("words").get().addOnSuccessListener { words ->
                        words.documents.mapNotNull { word ->
                            wordStages.add(
                                WordStage(
                                    id = word.id,
                                    stage = word["stage"].toString().toInt(),
                                    word = word["word"].toString(),
                                    isComplete = false
                                )
                            )
                        }
                    }

                    WordLevel(
                        id = docs.id,
                        level = level,
                        wordStages = wordStages,
                        isComplete = false
                    )
                }

                if (wordLevel != null) trySend(wordLevel)
            }

        awaitClose {
            Log.d(
                "FirebaseWordOrderService",
                "getWordOrderDataSource: Cancelling word order listener"
            )
            listener.remove()
        }
    }

    fun getUserWordOrderProgress(): Flow<MutableMap<String, Map<String, Boolean>>> =
        callbackFlow {
            val email = Firebase.auth.currentUser?.email
            val userDocument =
                Firebase.firestore.collection("users").whereEqualTo("email", email).limit(1).get()
                    .await()
            val userId = userDocument.documents.first().id

            val listener =
                Firebase.firestore.collection("users/${userId}/games/word-order/levelling")
                    .addSnapshotListener { value, error ->
                        if (error != null) {
                            Firebase.crashlytics.apply {
                                log("Error when get the word order data")
                                recordException(error)
                            }

                            cancel(message = "Error when get the word order data", cause = error)
                            return@addSnapshotListener
                        }

                        val levelProgressMap = mutableMapOf<String, Map<String, Boolean>>()

                        value?.documents?.forEach { docs ->
                            val wordsProgressMap = mutableMapOf<String, Boolean>()

                            docs.reference.collection("words").get().addOnSuccessListener { wordDocuments ->
                                wordDocuments.documents.forEach { words ->
                                    wordsProgressMap[words.id] =
                                        words.get("isComplete").toString().toBoolean()
                                }

                                levelProgressMap[docs.id] = wordsProgressMap
                                trySend(levelProgressMap)
                            }
                        }
                    }

            awaitClose {
                Log.d(
                    "FirebaseWordOrderService",
                    "getWordOrderDataSource: Cancelling word progress listener"
                )
                listener.remove()
            }
        }

    private const val TAG = "FirebaseWordOrderService"
}

