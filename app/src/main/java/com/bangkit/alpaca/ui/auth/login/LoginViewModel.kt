package com.bangkit.alpaca.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.alpaca.data.AuthRepository
import com.bangkit.alpaca.data.remote.Result
import com.bangkit.alpaca.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    private val _result = MutableLiveData<Event<Result<Boolean>>>()
    val result: LiveData<Event<Result<Boolean>>> get() = _result

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            authRepository.loginUser(email, password).collect { result ->
                _result.value = Event(result)
            }
        }
    }

}