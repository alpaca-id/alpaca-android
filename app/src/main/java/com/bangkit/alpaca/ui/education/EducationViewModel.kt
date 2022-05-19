package com.bangkit.alpaca.ui.education

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.alpaca.data.StoryRepository
import com.bangkit.alpaca.model.Flashcard
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EducationViewModel @Inject constructor(storyRepository: StoryRepository) :
    ViewModel() {

    val flashcards: LiveData<List<Flashcard>> =
        storyRepository.getAllFlashcardContent().asLiveData()
}