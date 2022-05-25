package com.bangkit.alpaca.ui.customization

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.alpaca.data.TextStyleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomizationViewModel @Inject constructor(private val textStyleRepository: TextStyleRepository) :
    ViewModel() {

    val textSizePreference: LiveData<Int> = textStyleRepository.getTextSizePreference().asLiveData()

    val textAlignmentPreference: LiveData<Int> =
        textStyleRepository.getTextAlignmentPreference().asLiveData()

    val lineHeightPreference: LiveData<Int> =
        textStyleRepository.getLineHeightPreference().asLiveData()

    val lineSpacingPreference: LiveData<Int> =
        textStyleRepository.getLineSpacingPreference().asLiveData()

    fun saveLineSpacingPreferences(value: Int) {
        viewModelScope.launch {
            textStyleRepository.saveLineSpacingPreference(value)
        }
    }

    fun saveLineHeightPreference(value: Int) {
        viewModelScope.launch {
            textStyleRepository.saveLineHeightPreference(value)
        }
    }

    fun saveTextAlignmentPreference(type: Int) {
        viewModelScope.launch {
            textStyleRepository.saveTextAlignmentPreference(type)
        }
    }

    fun saveTextSizePreference(size: Int) {
        viewModelScope.launch {
            textStyleRepository.saveTextSizePreference(size)
        }
    }

}