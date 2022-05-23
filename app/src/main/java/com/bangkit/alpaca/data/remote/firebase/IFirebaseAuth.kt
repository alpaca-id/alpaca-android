package com.bangkit.alpaca.data.remote.firebase

import com.bangkit.alpaca.data.remote.Result
import kotlinx.coroutines.flow.Flow

interface IFirebaseAuth {
    suspend fun loginUser(email: String, password: String): Flow<Result<Boolean>>
    suspend fun registrationUser(
        name: String,
        email: String,
        password: String
    ): Flow<Result<Boolean>>
    suspend fun sendPasswordReset(email: String): Flow<Result<Boolean>>
    fun logoutUser()
}