package com.bangkit.alpaca.data

import com.bangkit.alpaca.data.remote.Result
import com.bangkit.alpaca.data.remote.retrofit.ApiService
import com.bangkit.alpaca.model.Audio
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TextToSpeechRepository @Inject constructor(
    private val apiService: ApiService
) {

    fun getAudio(text: String): Flow<Result<Audio>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getAudio(text)
            val audio = Audio(response.audioUrl)
            emit(Result.Success(audio))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }
}