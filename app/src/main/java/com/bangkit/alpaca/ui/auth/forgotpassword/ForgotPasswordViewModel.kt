package com.bangkit.alpaca.ui.auth.forgotpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.alpaca.data.AuthRepository
import com.bangkit.alpaca.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {
    private val _result = MutableLiveData<Result<Boolean>>()
    val result: LiveData<Result<Boolean>> get() = _result

    fun sendPassword(email: String) {
        viewModelScope.launch {
            authRepository.sendPasswordReset(email).collect { result ->
                _result.value = result
            }
        }
    }
}