package ru.potemkin.orpheusjetpackcompose.presentation.viewmodels

import androidx.lifecycle.ViewModel
import ru.potemkin.orpheusjetpackcompose.data.repositories.UserRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.GetUserListUseCase

class ChatListViewModel : ViewModel() {

    private val repository = UserRepositoryImpl

    private val getUserListUseCase = GetUserListUseCase(repository)

    val userList = getUserListUseCase.getUserList()

}