package com.bangkit.alpaca.data

import com.bangkit.alpaca.data.remote.Result
import com.bangkit.alpaca.data.remote.firebase.IFirebaseUser
import com.bangkit.alpaca.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : IFirebaseUser {
    override suspend fun getUserData(): Flow<Result<User>> = flow {
        emit(Result.Loading)
        try {
            val currentEmail = auth.currentUser?.email
            val docRef = currentEmail?.let { db.collection("users").document(it) }

            val document = docRef?.get()?.await()
            val name = document?.getString("name")
            val email = document?.getString("email")
            val user = User(name, email)
            emit(Result.Success(user))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }
}