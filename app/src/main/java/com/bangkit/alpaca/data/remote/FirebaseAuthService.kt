package com.bangkit.alpaca.data.remote

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
        try {
            emit(Result.Loading)
            auth.createUserWithEmailAndPassword(email, password).await()

            val user = User(name, email)
            val pathString = FirebaseAuth.getInstance().currentUser?.uid

            pathString?.let {
                db.collection("users")
                    .add(user)
            }?.await()

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