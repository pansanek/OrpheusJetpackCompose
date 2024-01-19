package ru.potemkin.orpheusjetpackcompose.presentation.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.mappers.UsersMapper
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory
import ru.potemkin.orpheusjetpackcompose.data.repositories.UserRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.data.states.NewsScreenState
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.AddUserUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.GetUserListUseCase
import javax.inject.Inject

class ChatListViewModel @Inject constructor(
    private val getUserListUseCase: GetUserListUseCase,
    private val addUserUseCase: AddUserUseCase
) : ViewModel() {

    private val mapper = UsersMapper()
    init {
        loadUsers()
    }
    val userList = getUserListUseCase.getUserList()


    private fun loadUsers(){
        viewModelScope.launch {
            val response = ApiFactory.appUserApiService.getAllUsers()
            val users = mapper.mapUsers(response)
            for(user in users){
                addUserUseCase.addUserItem(user)
            }
            Log.d("USERS",users.toString())
        }
    }
}