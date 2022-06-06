package com.bangkit.alpaca.data.remote

import android.util.Log
import com.bangkit.alpaca.model.WordLevel
import com.bangkit.alpaca.model.WordStage
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

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
                                    isComplete = true
                                )
                            )
                        }
                    }

                    WordLevel(
                        id = docs.id,
                        level = level,
                        wordStages = wordStages,
                        isComplete = true
                    )
                }

                if (wordLevel != null) trySend(wordLevel)
            }

        awaitClose {
            Log.d("FirebaseWordOrderService", "getWordOrderDataSource: Cancelling word order listener")
            listener.remove()
        }
    }
}

