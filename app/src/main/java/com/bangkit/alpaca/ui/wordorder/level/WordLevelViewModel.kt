package com.bangkit.alpaca.ui.wordorder.level

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.bangkit.alpaca.data.WordOrderRepository
import com.bangkit.alpaca.data.remote.Result
import com.bangkit.alpaca.model.WordLevel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class WordLevelViewModel @Inject constructor(private val wordOrderRepository: WordOrderRepository) :
    ViewModel() {
    fun getWordLevelData(): LiveData<Result<List<WordLevel>>> = liveData {
        wordOrderRepository.getGameDataSource().collect { result ->
            when (result) {
                is Result.Loading -> emit(Result.Loading)
                is Result.Success -> {
                    wordOrderRepository.getGameProgressDataSource().collect { progress ->
                        result.data.forEach { wordLevel ->
                            val wordProgress = progress[wordLevel.id]

                            wordLevel.wordStages.forEach { wordStage ->
                                wordStage.isComplete = wordProgress?.get(wordStage.id) ?: false
                            }

                            if (wordLevel.wordStages.size == (wordProgress?.size ?: 0))
                                wordLevel.isComplete = true

                            Log.d(TAG, "getWordLevelData: $wordLevel")
                            Log.d(TAG, "getWordLevelData: $progress")

                        }

                        emit(result)
                    }
                }
                is Result.Error -> emit(Result.Error(result.error))
            }

        }
    }

    companion object {
        private const val TAG = "WordLevelViewModel"
    }
}