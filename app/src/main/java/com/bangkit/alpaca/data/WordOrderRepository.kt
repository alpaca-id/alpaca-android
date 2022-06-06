package com.bangkit.alpaca.data

import com.bangkit.alpaca.data.remote.FirebaseWordOrderService
import com.bangkit.alpaca.model.WordLevel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
class WordOrderRepository {
    fun getGameDataSource(): Flow<List<WordLevel>> = flow {
        FirebaseWordOrderService.getWordOrderDataSource().collect {
            emit(it)
        }
    }
}