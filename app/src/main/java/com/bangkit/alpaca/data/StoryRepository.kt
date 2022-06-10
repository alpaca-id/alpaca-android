package com.bangkit.alpaca.data

import com.bangkit.alpaca.data.local.entity.StoryEntity.Companion.toStory
import com.bangkit.alpaca.data.local.room.StoryDao
import com.bangkit.alpaca.data.remote.Result
import com.bangkit.alpaca.data.remote.firebase.FirebaseStoryService
import com.bangkit.alpaca.data.remote.retrofit.ApiService
import com.bangkit.alpaca.model.Flashcard
import com.bangkit.alpaca.model.Story
import com.bangkit.alpaca.model.Story.Companion.toStory
import com.bangkit.alpaca.utils.DataDummy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ExperimentalCoroutinesApi
class StoryRepository @Inject constructor(
    private val storyDao: StoryDao,
    private val apiService: ApiService
) {

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

    /**
     * Save a new story to user's collection
     *
     * @param story Story
     */
    suspend fun saveNewStory(story: Story) {
        FirebaseStoryService.getUserDocumentID().collect { documentId ->
            FirebaseStoryService.saveNewStory(documentId, story)
        }
    }

    /**
     * Delete a story from user's collection
     *
     * @param story Story
     */
    suspend fun deleteStory(story: Story) {
        FirebaseStoryService.getUserDocumentID().collect { documentId ->
            FirebaseStoryService.deleteStory(documentId, story)
        }
    }

    fun getAllFlashcardContent(): Flow<List<Flashcard>> = flow {
        emit(DataDummy.provideFlashcard())
    }

    fun getAllStoryBook(): Flow<Result<List<Story>>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getAllStoryBook()
            val storyItems = response.data.stories
            val stories = storyItems.map { storyItem ->
                storyItem.toStory()
            }
            storyDao.deleteAllStoryBook()
            storyDao.insertAllStoryBook(stories)
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }

        val localData: Flow<Result<List<Story>>> =
            storyDao.getAllStoryBook().map {
                val story = it.map { storyEntity ->
                    storyEntity.toStory()
                }
                Result.Success(story)
            }

        localData.collect {
            emit(it)
        }
    }

    fun getSearchStoryBook(keyword: String): Flow<Result<List<Story>>> = flow {
        val result = storyDao.getSearchStoryBook("%$keyword%").map {
            val story = it.map { storyEntity ->
                storyEntity.toStory()
            }
            Result.Success(story)
        }

        result.collect {
            emit(it)
        }
    }
}