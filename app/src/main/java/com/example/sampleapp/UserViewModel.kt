package com.example.sampleapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User> = _user

    private val _state: MutableLiveData<UserState> = MutableLiveData()
    val state: LiveData<UserState> = _state

    fun register() {
        viewModelScope.launch {
            _state.value = UserState.REGISTERING
            try {
                val user = repository.register()
                _user.value = user
                _state.value = UserState.REGISTERED
            } catch (e: Throwable) {
                _state.value = UserState.UNREGISTERED
            }
        }
    }
}