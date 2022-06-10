package com.bangkit.alpaca.data.remote.firebase

import com.bangkit.alpaca.data.remote.Result
import com.bangkit.alpaca.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

object FirebaseAuthService {
    fun loginUser(email: String, password: String) = flow {
        val auth = Firebase.auth
        try {
            emit(Result.Loading)
            auth.signInWithEmailAndPassword(email, password).await()
            emit(Result.Success(true))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun registrationUser(
        name: String,
        email: String,
        password: String
    ) = flow {
        val auth = Firebase.auth
        val db = Firebase.firestore

        val initWordOrder = hashMapOf(
            "id" to "word-order"
        )

        val initLeveling = hashMapOf(
            "isComplete" to false
        )

        try {
            emit(Result.Loading)
            auth.createUserWithEmailAndPassword(email, password).await()

            val user = User(name, email)
            val pathString = FirebaseAuth.getInstance().currentUser?.uid

            pathString?.let {
                db.collection("users")
                    .add(user)
            }?.await()

            val users = db.collection("users").whereEqualTo("email", email).limit(1).get().await()
            val id = users.documents.first().id

            db.document("/users/$id/games/word-order").apply {
                set(initWordOrder).await()
                collection("levelling").document("0001").set(initLeveling).await()
                collection("levelling/0001/words").document("0001").set(initLeveling).await()
            }

            emit(Result.Success(true))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun sendPasswordReset(email: String) = flow {
        val auth = Firebase.auth
        try {
            emit(Result.Loading)
            auth.sendPasswordResetEmail(email).await()
            emit(Result.Success(true))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun logoutUser() {
        val auth = Firebase.auth
        auth.signOut()
    }
}