package com.bangkit.alpaca.ui.auth.forgotpassword

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.alpaca.utils.Result
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordViewModel : ViewModel() {
    private val _result = MutableLiveData<Result<Boolean>>()
    val result: LiveData<Result<Boolean>> get() = _result

    fun sendPassword(activity: Activity, email: String) {
        _result.value = Result.Loading
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    _result.value = Result.Success(true)
                } else {
                    _result.value =
                        task.exception?.message?.let { message -> Result.Error(message) }
                }
            }
    }
}