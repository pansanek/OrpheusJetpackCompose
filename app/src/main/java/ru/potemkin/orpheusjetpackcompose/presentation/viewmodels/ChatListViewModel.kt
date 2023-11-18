package ru.potemkin.orpheusjetpackcompose.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import ru.potemkin.orpheusjetpackcompose.data.repositories.UserRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.GetUserListUseCase
import javax.inject.Inject

class ChatListViewModel @Inject constructor(
    private val getUserListUseCase: GetUserListUseCase
) : ViewModel() {

    val userList = getUserListUseCase.getUserList()

}