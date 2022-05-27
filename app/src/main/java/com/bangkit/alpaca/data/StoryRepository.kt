package com.bangkit.alpaca.data

import com.bangkit.alpaca.data.remote.FirebaseStoryService
import com.bangkit.alpaca.model.Flashcard
import com.bangkit.alpaca.model.Story
import com.bangkit.alpaca.utils.DataDummy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
class StoryRepository {

    /**
     * Provide all stories collection of the user
     *
     * @return Flow
     */
    fun getAllStoriesCollection(): Flow<List<Story>?> = flow {
        FirebaseStoryService.getUserDocumentID().collect { documentId ->
            FirebaseStoryService.getUserStoriesScan(documentId).collect { stories ->
                emit(stories)
            }
        }
    }

    fun getAllStoryLibrary(): Flow<List<Story>?> = flow {
        FirebaseStoryService.getLibraryStory().collect {
            emit(it)
        }
    }

    fun getAllFlashcardContent(): Flow<List<Flashcard>> = flow {
        emit(DataDummy.provideFlashcard())
    }
}