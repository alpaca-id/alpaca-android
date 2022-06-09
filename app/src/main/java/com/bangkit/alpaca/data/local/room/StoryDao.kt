package com.bangkit.alpaca.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bangkit.alpaca.data.local.entity.StoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllStoryBook(stories: List<StoryEntity>)

    @Query("DELETE FROM story")
    suspend fun deleteAllStoryBook()

    @Query("SELECT * FROM story")
    fun getAllStoryBook(): Flow<List<StoryEntity>>

    @Query("SELECT * FROM story WHERE title LIKE :keyword")
    fun getSearchStoryBook(keyword: String): Flow<List<StoryEntity>>
}