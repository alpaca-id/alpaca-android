package com.bangkit.alpaca.data

import com.bangkit.alpaca.data.local.room.StoryDao
import com.bangkit.alpaca.model.Flashcard
import com.bangkit.alpaca.model.Story
import com.bangkit.alpaca.utils.DataDummy
import com.bangkit.alpaca.utils.toStory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StoryRepository @Inject constructor(private val storyDao: StoryDao) {


    /**
     * Get all saved stories from datasource
     *
     * @return Flow
     */
    fun getSavedStories(): Flow<List<Story>> = flow {
        // Convert StoryEntity to Story
        storyDao.getAllStories().collect { list ->
            val stories = mutableListOf<Story>()

            list.forEach { storyEntity ->
                stories.add(storyEntity.toStory())
            }
        }
    }

    fun getAllFlashcardContent(): Flow<List<Flashcard>> = flow {
        emit(DataDummy.provideFlashcard())
    }
}