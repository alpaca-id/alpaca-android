package com.bangkit.alpaca.data

import com.bangkit.alpaca.data.remote.FirebaseAuthService
import com.bangkit.alpaca.data.remote.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepository @Inject constructor() {
    fun loginUsers(email: String, password: String): Flow<Result<Boolean>> =
        FirebaseAuthService.loginUser(email, password)

    fun registrationUser(
        name: String,
        email: String,
        password: String
    ): Flow<Result<Boolean>> = FirebaseAuthService.registrationUser(name, email, password)

    fun sendPasswordReset(email: String): Flow<Result<Boolean>> =
        FirebaseAuthService.sendPasswordReset(email)

    fun logoutUser() {
        FirebaseAuthService.logoutUser()
    }
}