package com.bangkit.alpaca.ui.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.alpaca.data.StoryRepository
import com.bangkit.alpaca.data.remote.Result
import com.bangkit.alpaca.model.Story
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class LibraryViewModel @Inject constructor(storyRepository: StoryRepository) :
    ViewModel() {
    val storiesLibrary: LiveData<Result<List<Story>>> =
        storyRepository.getAllBookStory().asLiveData()
}