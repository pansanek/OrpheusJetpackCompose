package ru.potemkin.orpheusjetpackcompose.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.mappers.AuthMapper
import ru.potemkin.orpheusjetpackcompose.data.mappers.UsersMapper
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory
import javax.inject.Inject

class AuthViewModel @Inject constructor(): ViewModel() {
    private val mapper = UsersMapper()

    init {
        loadAuthItems()
    }

    private fun loadAuthItems() {
        viewModelScope.launch{
            val users = ApiFactory.apiService.loadAllUsers()
            val authItems = mapper.mapUsers(users)
        }
    }

}