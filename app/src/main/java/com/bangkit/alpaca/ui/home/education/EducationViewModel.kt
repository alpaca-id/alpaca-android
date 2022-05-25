package com.bangkit.alpaca.ui.home.education

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.alpaca.data.StoryRepository
import com.bangkit.alpaca.model.Flashcard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class EducationViewModel @Inject constructor(storyRepository: StoryRepository) :
    ViewModel() {

    val flashcards: LiveData<List<Flashcard>> =
        storyRepository.getAllFlashcardContent().asLiveData()
}