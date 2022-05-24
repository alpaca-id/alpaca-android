package com.bangkit.alpaca.di

import com.bangkit.alpaca.data.StoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@ExperimentalCoroutinesApi
class RepositoryModule {

    @Provides
    @Singleton
    fun provideStoryRepository(): StoryRepository = StoryRepository()
}