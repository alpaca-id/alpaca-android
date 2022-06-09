package com.bangkit.alpaca.di

import com.bangkit.alpaca.data.AuthRepository
import com.bangkit.alpaca.data.StoryRepository
import com.bangkit.alpaca.data.UserRepository
import com.bangkit.alpaca.data.WordOrderRepository
import com.bangkit.alpaca.data.local.room.StoryDao
import com.bangkit.alpaca.data.remote.TextToSpeechRepository
import com.bangkit.alpaca.data.remote.retrofit.ApiService
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
    fun provideStoryRepository(storyDao: StoryDao, apiService: ApiService): StoryRepository =
        StoryRepository(storyDao, apiService)

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository = AuthRepository()

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository = UserRepository()

    @Provides
    @Singleton
    fun provideWordOrderRepository(): WordOrderRepository = WordOrderRepository()

    @Provides
    @Singleton
    fun provideTextToSpeechRepository(apiService: ApiService): TextToSpeechRepository =
        TextToSpeechRepository(apiService)
}