package com.bangkit.alpaca.data

import com.bangkit.alpaca.data.remote.FirebaseWordOrderService
import com.bangkit.alpaca.model.WordLevel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
class WordOrderRepository {
    fun getGameDataSource(): Flow<List<WordLevel>> =
        FirebaseWordOrderService.getWordOrderDataSource()

    fun getGameProgressDataSource(): Flow<MutableMap<String, Map<String, Boolean>>> =
        FirebaseWordOrderService.getUserWordOrderProgress()

}
