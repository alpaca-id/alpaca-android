package com.bangkit.alpaca.ui.wordorder.level

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.alpaca.data.WordOrderRepository
import com.bangkit.alpaca.model.WordLevel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class WordLevelViewModel @Inject constructor(private val wordOrderRepository: WordOrderRepository) :
    ViewModel() {
    fun getGameDataSource(): LiveData<List<WordLevel>?> =
        wordOrderRepository.getGameDataSource().asLiveData()
}