package com.bangkit.alpaca.data.remote

import com.bangkit.alpaca.model.User
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

object FirebaseUserService {
    fun getUserData(): Flow<Result<User>> = flow {
        val auth = Firebase.auth
        val db = Firebase.firestore
        emit(Result.Loading)
        try {
            val currentEmail = auth.currentUser?.email
            val docRef =
                currentEmail?.let {
                    db.collection("users").whereEqualTo("email", currentEmail).get()
                }?.await()

            val document = docRef?.documents?.get(0)

            val name = document?.getString("name")
            val email = document?.getString("email")
            if (email != null && name != null) {
                val user = User(name, email)
                emit(Result.Success(user))
            } else {
                emit(Result.Error(""))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun updateUserData(
        email: String,
        name: String,
        password: String
    ): Flow<Result<Boolean>> = flow {
        val auth = Firebase.auth
        val db = Firebase.firestore
        emit(Result.Loading)
        try {
            val currentEmail = auth.currentUser?.email
            val user = FirebaseAuth.getInstance().currentUser
            val credential = currentEmail?.let { EmailAuthProvider.getCredential(it, password) }
            if (credential != null) {
                user?.reauthenticate(credential)?.await()
            }
            auth.currentUser?.updateEmail(email)?.await()

            val querySnapshot =
                currentEmail?.let {
                    db.collection("users").whereEqualTo("email", currentEmail).get()
                }?.await()
            val id = querySnapshot?.documents?.get(0)?.id

            val docRef = id?.let { db.collection("users").document(it) }
            docRef?.update("email", email)
            docRef?.update("name", name)
            emit(Result.Success(true))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun updatePassword(
        currentPassword: String,
        newPassword: String
    ): Flow<Result<Boolean>> = flow {
        val auth = Firebase.auth
        emit(Result.Loading)
        try {
            val currentEmail = auth.currentUser?.email
            val user = FirebaseAuth.getInstance().currentUser

            val credential =
                currentEmail?.let { EmailAuthProvider.getCredential(it, currentPassword) }
            if (credential != null) {
                user?.reauthenticate(credential)?.await()
            }

            auth.currentUser?.updatePassword(newPassword)?.await()
            emit(Result.Success(true))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }
}