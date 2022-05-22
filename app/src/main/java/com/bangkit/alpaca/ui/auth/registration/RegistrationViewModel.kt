package com.bangkit.alpaca.ui.auth.registration

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.alpaca.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegistrationViewModel : ViewModel() {
    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean>
        get() = _isSuccess

    fun registrationUser(
        activity: Activity,
        name: String,
        email: String,
        password: String,
        auth: FirebaseAuth
    ) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task1 ->
                if (task1.isSuccessful) {
                    val user = User(name, email)
                    FirebaseAuth.getInstance().currentUser?.uid?.let {
                        val db = Firebase.firestore
                        db.collection("users")
                            .document(user.email)
                            .set(user)
                            .addOnSuccessListener {
                                _isSuccess.value = true
                            }
                            .addOnFailureListener {
                                _isSuccess.value = false
                            }
                    }
                }
            }
    }
}