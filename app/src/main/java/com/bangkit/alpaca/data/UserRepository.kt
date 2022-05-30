package com.bangkit.alpaca.data

import com.bangkit.alpaca.data.remote.FirebaseUserService
import com.bangkit.alpaca.data.remote.Result
import com.bangkit.alpaca.model.User
import kotlinx.coroutines.flow.Flow

class UserRepository{
    fun getUserData(): Flow<Result<User>> = FirebaseUserService.getUserData()

    fun updateUserData(
        email: String,
        name: String,
        password: String
    ): Flow<Result<Boolean>> = FirebaseUserService.updateUserData(email, name, password)

    fun updatePassword(
        currentPassword: String,
        newPassword: String
    ): Flow<Result<Boolean>> = FirebaseUserService.updatePassword(currentPassword, newPassword)
}