package com.bangkit.alpaca.ui.processing.confirmation

import androidx.lifecycle.ViewModel
import com.bangkit.alpaca.data.remote.FirebaseStoryService
import com.bangkit.alpaca.model.Story
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ConfirmationViewModel : ViewModel() {

    /**
     * Save new story to the database
     *
     * @param story Story
     */
    fun saveNewStory(story: Story) {
        FirebaseStoryService.saveNewStory(story)
    }
}