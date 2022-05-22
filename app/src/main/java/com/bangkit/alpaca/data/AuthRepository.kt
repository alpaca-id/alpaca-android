package com.bangkit.alpaca.data

import com.bangkit.alpaca.data.remote.firebase.IFirebaseAuth
import com.bangkit.alpaca.model.User
import com.bangkit.alpaca.utils.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(private val auth: FirebaseAuth) : IFirebaseAuth {

    override suspend fun loginUser(email: String, password: String) = flow {
        try {
            emit(Result.Loading)
            auth.signInWithEmailAndPassword(email, password).await()
            emit(Result.Success(true))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    override suspend fun registrationUser(
        name: String,
        email: String,
        password: String
    ) = flow {
        try {
            emit(Result.Loading)
            auth.createUserWithEmailAndPassword(email, password).await()

            val user = User(name, email)
            val fb = FirebaseDatabase.getInstance()
            val pathString = FirebaseAuth.getInstance().currentUser?.uid

            pathString?.let {
                fb.getReference("Users")
                    .child(it)
                    .setValue(user)
            }?.await()

            emit(Result.Success(true))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    override suspend fun sendPasswordReset(email: String) = flow {
        try {
            emit(Result.Loading)
            auth.sendPasswordResetEmail(email).await()
            emit(Result.Success(true))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }

    }
}