package com.bangkit.alpaca.ui.wordorder.stage

import androidx.lifecycle.*
import com.bangkit.alpaca.data.WordOrderRepository
import com.bangkit.alpaca.data.remote.Result
import com.bangkit.alpaca.data.TextToSpeechRepository
import com.bangkit.alpaca.model.Audio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class WordStageViewModel @Inject constructor(
    private val wordOrderRepository: WordOrderRepository,
    private val textToSpeechRepository: TextToSpeechRepository
) :
    ViewModel() {
    private val mText = MutableLiveData<String>()
    val audio: LiveData<Result<Audio>> = Transformations.switchMap(mText, ::getAudio)

    fun userProgressUpdate(
        level: String,
        stage: String,
        isLevelComplete: Boolean
    ): LiveData<Result<Boolean>> = liveData {
        wordOrderRepository.userProgressUpdate(level, stage, isLevelComplete).collect {
            emit(it)
        }
    }

    private fun getAudio(text: String) = textToSpeechRepository.getAudio(text).asLiveData()

    fun getTextToSpeech(text: String) = apply {
        mText.value = text
    }
}