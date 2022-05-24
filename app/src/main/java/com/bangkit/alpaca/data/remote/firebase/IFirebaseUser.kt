package com.bangkit.alpaca.data.remote.firebase

import com.bangkit.alpaca.data.remote.Result
import com.bangkit.alpaca.model.User
import kotlinx.coroutines.flow.Flow

interface IFirebaseUser {
    suspend fun getUserData(): Flow<Result<User>>
}