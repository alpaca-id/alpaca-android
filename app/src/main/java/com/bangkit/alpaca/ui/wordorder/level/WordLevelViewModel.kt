package com.bangkit.alpaca.ui.wordorder.level

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.bangkit.alpaca.data.WordOrderRepository
import com.bangkit.alpaca.model.WordLevel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class WordLevelViewModel @Inject constructor(private val wordOrderRepository: WordOrderRepository) :
    ViewModel() {
    fun getWordLevelData(): LiveData<List<WordLevel>> = liveData {
        wordOrderRepository.getGameDataSource().collect { wordLevels ->
            wordOrderRepository.getGameProgressDataSource().collect { progress ->

                wordLevels.forEach { wordLevel ->
                    val wordProgress = progress[wordLevel.id]

                    wordLevel.wordStages.forEach { wordStage ->
                        wordStage.isComplete = wordProgress?.get(wordStage.id) ?: false
                    }

                    if (wordLevel.wordStages.size == (wordProgress?.size ?: 0))
                        wordLevel.isComplete = true

                    Log.d(TAG, "getWordLevelData: $wordLevel")
                    Log.d(TAG, "getWordLevelData: $progress")

                }

                emit(wordLevels)
            }
        }
    }

    companion object {
        private const val TAG = "WordLevelViewModel"
    }
}