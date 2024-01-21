package ru.potemkin.orpheusjetpackcompose.presentation.main

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {

    private val _authState = MutableLiveData<AuthState>(AuthState.Initial)
    val authState: LiveData<AuthState> = _authState

    init {
//        val storage = VKPreferencesKeyValueStorage(application)
//        val token = VKAccessToken.restore(storage)
//        val loggedIn = token != null && token.isValid
//        Log.d("MainViewModel", "Token: ${token?.accessToken}")
        _authState.value = AuthState.NotAuthorized
    }

    fun performAuthResult(result: String) {
        if (result == "Authorized") {
            _authState.value = AuthState.Authorized
        } else {
            _authState.value = AuthState.NotAuthorized
        }
    }
}