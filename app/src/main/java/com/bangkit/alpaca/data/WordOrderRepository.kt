package com.bangkit.alpaca.data

import com.bangkit.alpaca.data.remote.firebase.FirebaseWordOrderService
import com.bangkit.alpaca.data.remote.Result
import com.bangkit.alpaca.model.WordLevel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
class WordOrderRepository {
    fun getGameDataSource(): Flow<Result<List<WordLevel>>> =
        FirebaseWordOrderService.getWordOrderDataSource()

    fun getGameProgressDataSource(): Flow<MutableMap<String, Map<String, Boolean>>> =
        FirebaseWordOrderService.getUserWordOrderProgress()

    fun userProgressUpdate(
        level: String,
        stage: String,
        isLevelComplete: Boolean
    ): Flow<Result<Boolean>> =
        FirebaseWordOrderService.userProgressUpdate(level, stage, isLevelComplete)

}
