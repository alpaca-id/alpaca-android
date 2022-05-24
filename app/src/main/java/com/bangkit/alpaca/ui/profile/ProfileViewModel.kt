package com.bangkit.alpaca.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.alpaca.data.UserRepository
import com.bangkit.alpaca.data.remote.Result
import com.bangkit.alpaca.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    private val _user = MutableLiveData<Result<User>>()
    val user: LiveData<Result<User>> get() = _user

    private val _result = MutableLiveData<Result<Boolean>>()
    val result: LiveData<Result<Boolean>> get() = _result

    fun getUserData() {
        viewModelScope.launch {
            userRepository.getUserData().collect { user ->
                _user.value = user
            }
        }
    }

    fun updateUserData(email: String, name: String, password:String) {
        viewModelScope.launch {
            userRepository.updateUserData(email, name, password).collect { result ->
                _result.value = result
            }
        }
    }
}