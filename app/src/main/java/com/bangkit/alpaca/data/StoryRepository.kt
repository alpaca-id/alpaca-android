package com.bangkit.alpaca.data

import com.bangkit.alpaca.model.Flashcard
import com.bangkit.alpaca.utils.DataDummy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class StoryRepository {

    fun getAllFlashcardContent(): Flow<List<Flashcard>> = flow {
        emit(DataDummy.provideFlashcard())
    }
}