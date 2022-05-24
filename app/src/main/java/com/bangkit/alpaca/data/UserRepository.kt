package com.bangkit.alpaca.data

import com.bangkit.alpaca.data.remote.FirebaseUserService
import com.bangkit.alpaca.data.remote.Result
import com.bangkit.alpaca.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor() {
    fun getUserData(): Flow<Result<User>> = FirebaseUserService.getUserData()

    fun updateUserData(
        email: String,
        name: String,
        password: String
    ): Flow<Result<Boolean>> = FirebaseUserService.updateUserData(email, name, password)
}