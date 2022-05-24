package com.bangkit.alpaca.ui.processing.confirmation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.alpaca.data.remote.FirebaseStoryService
import com.bangkit.alpaca.model.Story
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class ConfirmationViewModel : ViewModel() {

    /**
     * Save new story to the database
     *
     * @param story Story
     */
    fun saveNewStory(story: Story) {
        viewModelScope.launch {
            val email = Firebase.auth.currentUser?.email
            FirebaseStoryService.getUserDocumentID(email).collect { documentId ->
                FirebaseStoryService.saveNewStory(documentId, story)
            }
        }
    }
}