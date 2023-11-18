package ru.potemkin.orpheusjetpackcompose.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import ru.potemkin.orpheusjetpackcompose.data.repositories.MessageRepositoryImpl

import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.GetMessageListUseCase
import javax.inject.Inject

class ChatViewModel @Inject constructor(application: Application): ViewModel() {

    private val repository = MessageRepositoryImpl(application)

    private val getMessageListUseCase = GetMessageListUseCase(repository)

    val messageList = getMessageListUseCase.getMessageList()

}