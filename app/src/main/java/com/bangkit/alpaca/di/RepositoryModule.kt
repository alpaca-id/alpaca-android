package com.bangkit.alpaca.di

import com.bangkit.alpaca.data.AuthRepository
import com.bangkit.alpaca.data.StoryRepository
import com.bangkit.alpaca.data.UserRepository
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

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository = AuthRepository()

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository = UserRepository()
}