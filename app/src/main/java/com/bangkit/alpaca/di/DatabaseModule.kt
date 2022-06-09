package com.bangkit.alpaca.di

import android.content.Context
import androidx.room.Room
import com.bangkit.alpaca.data.local.room.StoryDao
import com.bangkit.alpaca.data.local.room.StoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun providesStoryDao(storyDatabase: StoryDatabase): StoryDao = storyDatabase.storyDao()

    @Provides
    @Singleton
    fun provideStoryDatabase(@ApplicationContext context: Context): StoryDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            StoryDatabase::class.java,
            "story_database"
        ).build()
    }
}