package ru.potemkin.orpheusjetpackcompose.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import ru.potemkin.orpheusjetpackcompose.data.repositories.UserRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.GetUserListUseCase
import javax.inject.Inject

class ChatListViewModel @Inject constructor(application: Application) : ViewModel() {

    private val repository = UserRepositoryImpl(application)

    private val getUserListUseCase = GetUserListUseCase(repository)

    val userList = getUserListUseCase.getUserList()

}