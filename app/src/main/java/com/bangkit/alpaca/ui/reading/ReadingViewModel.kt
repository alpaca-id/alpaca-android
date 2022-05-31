package com.bangkit.alpaca.ui.reading

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.alpaca.data.TextStyleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReadingViewModel @Inject constructor(private val textStyleRepository: TextStyleRepository) :
    ViewModel() {
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

}