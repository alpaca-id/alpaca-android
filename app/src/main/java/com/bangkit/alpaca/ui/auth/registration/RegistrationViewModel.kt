package com.bangkit.alpaca.ui.auth.registration

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.alpaca.model.User
import com.bangkit.alpaca.utils.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistrationViewModel : ViewModel() {
    private val _result = MutableLiveData<Result<Boolean>>()
    val result: LiveData<Result<Boolean>> get() = _result

    fun registrationUser(
        activity: Activity,
        name: String,
        email: String,
        password: String,
        auth: FirebaseAuth
    ) {
        _result.value = Result.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task1 ->
                if (task1.isSuccessful) {
                    val user = User(name, email)
                    FirebaseAuth.getInstance().currentUser?.uid?.let { pathString ->
                        FirebaseDatabase.getInstance().getReference("Users")
                            .child(pathString)
                            .setValue(user)
                            .addOnCompleteListener(activity) { task2 ->
                                if (task2.isSuccessful) {
                                    _result.value = Result.Success(true)
                                } else {
                                    _result.value =
                                        task2.exception?.message?.let { message ->
                                            Result.Error(
                                                message
                                            )
                                        }
                                }
                            }
                    }
                } else {
                    _result.value =
                        task1.exception?.message?.let { message ->
                            Result.Error(
                                message
                            )
                        }
                }
            }
    }
}