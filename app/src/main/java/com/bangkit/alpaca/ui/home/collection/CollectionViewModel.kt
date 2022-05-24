package com.bangkit.alpaca.ui.home.collection

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.bangkit.alpaca.data.remote.FirebaseStoryService
import com.bangkit.alpaca.model.Story
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
class CollectionViewModel : ViewModel() {

    val storiesCollection: LiveData<List<Story>?> = liveData {
        val email = Firebase.auth.currentUser?.email
        FirebaseStoryService.getUserDocumentID(email).collect { documentId ->
            FirebaseStoryService.getUserStoriesScan(documentId).collect { stories ->
                emit(stories)
            }
        }
    }
}