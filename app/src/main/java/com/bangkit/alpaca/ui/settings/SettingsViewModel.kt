package com.bangkit.alpaca.ui.settings

import androidx.lifecycle.ViewModel
import com.bangkit.alpaca.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {

    fun logoutUser() {
        authRepository.logoutUser()
    }
}