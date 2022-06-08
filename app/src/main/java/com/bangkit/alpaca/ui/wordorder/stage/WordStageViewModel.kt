package com.bangkit.alpaca.ui.wordorder.stage

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.bangkit.alpaca.data.WordOrderRepository
import com.bangkit.alpaca.data.remote.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class WordStageViewModel @Inject constructor(private val wordOrderRepository: WordOrderRepository) :
    ViewModel() {
    fun userProgressUpdate(
        level: String,
        stage: String,
        isLevelComplete: Boolean
    ): LiveData<Result<Boolean>> = liveData {
        wordOrderRepository.userProgressUpdate(level, stage, isLevelComplete).collect {
            emit(it)
        }
    }
}