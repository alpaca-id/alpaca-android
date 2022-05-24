package com.bangkit.alpaca.ui.collection

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.bangkit.alpaca.data.StoryRepository
import com.bangkit.alpaca.data.remote.FirebaseStoryService
import com.bangkit.alpaca.model.Story
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class CollectionViewModel @Inject constructor(storyRepository: StoryRepository) :
    ViewModel() {

    val storiesCollection: LiveData<List<Story>?> = liveData {
        FirebaseStoryService.getUserDocumentID().collect { documentId ->
            FirebaseStoryService.getUserStoriesScan(documentId).collect { stories ->
                emit(stories)
            }
        }
    }
}