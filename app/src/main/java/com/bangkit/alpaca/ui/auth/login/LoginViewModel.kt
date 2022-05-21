package com.bangkit.alpaca.ui.auth.login

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.alpaca.utils.Result
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {
    private val _result = MutableLiveData<Result<Boolean>>()
    val result: LiveData<Result<Boolean>> get() = _result

    fun loginUser(activity: Activity, email: String, password: String, auth: FirebaseAuth) {
        _result.value = Result.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    _result.value = Result.Success(task.isSuccessful)
                } else {
                    _result.value =
                        task.exception?.message?.let { message -> Result.Error(message) }
                }
            }
    }

}