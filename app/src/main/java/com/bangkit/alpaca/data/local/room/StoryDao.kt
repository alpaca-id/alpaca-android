package com.bangkit.alpaca.data.local.room

import androidx.room.*
import com.bangkit.alpaca.data.local.entity.StoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StoryDao {
    @Query("SELECT * FROM stories")
    fun getAllStories(): Flow<List<StoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStories(vararg stories: StoryEntity)

    @Delete
    fun deleteStory(storyEntity: StoryEntity)
}