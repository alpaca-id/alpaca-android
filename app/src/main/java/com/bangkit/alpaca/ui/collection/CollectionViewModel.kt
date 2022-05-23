package com.bangkit.alpaca.ui.collection

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.alpaca.data.remote.FirebaseStoryService
import com.bangkit.alpaca.model.Story
import kotlinx.coroutines.ExperimentalCoroutinesApi

class CollectionViewModel : ViewModel() {

    @ExperimentalCoroutinesApi
    val storiesCollection: LiveData<List<Story>?> =
        FirebaseStoryService.getUserStoriesScan().asLiveData()
}