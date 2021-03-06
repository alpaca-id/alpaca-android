package com.bangkit.alpaca.ui.reading

import androidx.lifecycle.*
import com.bangkit.alpaca.data.StoryRepository
import com.bangkit.alpaca.data.TextStyleRepository
import com.bangkit.alpaca.data.TextToSpeechRepository
import com.bangkit.alpaca.data.remote.Result
import com.bangkit.alpaca.model.Audio
import com.bangkit.alpaca.model.Story
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class ReadingViewModel @Inject constructor(
    private val storyRepository: StoryRepository,
    private val textStyleRepository: TextStyleRepository,
    private val textToSpeechRepository: TextToSpeechRepository
) :
    ViewModel() {

    private val mText = MutableLiveData<String>()
    val audio: LiveData<Result<Audio>> = Transformations.switchMap(mText, ::getAudio)

    fun getTextBackgroundPreference(): LiveData<Int> =
        textStyleRepository.getTextBackgroundPreference().asLiveData()

    fun getLineSpacingPreference(): LiveData<Int> =
        textStyleRepository.getLineSpacingPreference().asLiveData()

    fun getLineHeightPreference(): LiveData<Int> =
        textStyleRepository.getLineHeightPreference().asLiveData()

    fun getTextAlignmentPreference(): LiveData<Int> =
        textStyleRepository.getTextAlignmentPreference().asLiveData()

    fun getTextSizePreference(): LiveData<Int> =
        textStyleRepository.getTextSizePreference().asLiveData()

    private fun getAudio(text: String) = textToSpeechRepository.getAudio(text).asLiveData()

    fun getTextToSpeech(text: String) = apply {
        mText.value = text
    }

    fun deleteStory(story: Story) {
        viewModelScope.launch {
            storyRepository.deleteStory(story)
        }
    }
}
