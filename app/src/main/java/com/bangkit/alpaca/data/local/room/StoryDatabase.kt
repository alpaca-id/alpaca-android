package com.bangkit.alpaca.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bangkit.alpaca.data.local.entity.StoryEntity

@Database(entities = [StoryEntity::class], version = 1, exportSchema = false)
abstract class StoryDatabase : RoomDatabase() {
    abstract fun storyDao(): StoryDao
}