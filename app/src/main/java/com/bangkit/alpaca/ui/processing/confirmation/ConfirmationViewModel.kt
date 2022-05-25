package com.bangkit.alpaca.ui.processing.confirmation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.alpaca.data.StoryRepository
import com.bangkit.alpaca.model.Story
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class ConfirmationViewModel @Inject constructor(private val storyRepository: StoryRepository) :
    ViewModel() {

    /**
     * Save new story to the database
     *
     * @param story Story
     */
    fun saveNewStory(story: Story) {
        viewModelScope.launch {
            storyRepository.saveNewStory(story)
        }
    }
}