package com.bangkit.alpaca.ui.home.education

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.bangkit.alpaca.model.Flashcard
import com.bangkit.alpaca.utils.DataDummy

class EducationViewModel : ViewModel() {

    val flashcards: LiveData<List<Flashcard>> = liveData {
        emit(DataDummy.provideFlashcard())
    }
}