package com.bangkit.alpaca.ui.processing.confirmation

import androidx.lifecycle.ViewModel
import com.bangkit.alpaca.model.Story
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ConfirmationViewModel : ViewModel() {

    fun saveNewStory(story: Story) {
        val db = Firebase.firestore
        val user = Firebase.auth.currentUser
        try {
            db.collection("users/${user?.email}/stories-scan")
                .add(story)

            db.collection("users").document("").get().addOnSuccessListener {
                it.id
            }
        } catch (e: Exception) {
            // Exception handler
        }
    }
}