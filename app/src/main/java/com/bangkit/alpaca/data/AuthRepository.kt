package com.bangkit.alpaca.data

import com.bangkit.alpaca.data.remote.firebase.IFirebaseAuth
import com.bangkit.alpaca.utils.Result
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
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
    ): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun sendPasswordReset(email: String): Flow<Result<Boolean>> {
        TODO("Not yet implemented")
    }
}