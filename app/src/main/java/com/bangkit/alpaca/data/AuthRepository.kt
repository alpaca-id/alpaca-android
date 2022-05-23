package com.bangkit.alpaca.data

import com.bangkit.alpaca.data.remote.Result
import com.bangkit.alpaca.data.remote.firebase.IFirebaseAuth
import com.bangkit.alpaca.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : IFirebaseAuth {

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
            val pathString = FirebaseAuth.getInstance().currentUser?.uid

            pathString?.let {
                db.collection("users")
                    .document(user.email)
                    .set(user)
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

    override fun logoutUser() {
        auth.signOut()
    }
}