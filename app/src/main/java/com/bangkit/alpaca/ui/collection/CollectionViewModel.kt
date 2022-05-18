package com.bangkit.alpaca.ui.collection

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.bangkit.alpaca.data.StoryRepository
import com.bangkit.alpaca.data.model.Story
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(private val storyRepository: StoryRepository) :
    ViewModel() {

    val storiesCollection: LiveData<List<Story>> = liveData {
        emit(storyRepository.getSavedStories())
    }
}