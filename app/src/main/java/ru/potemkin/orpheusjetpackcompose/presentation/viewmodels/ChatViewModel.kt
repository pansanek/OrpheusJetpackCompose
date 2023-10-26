package ru.potemkin.orpheusjetpackcompose.presentation.viewmodels

import androidx.lifecycle.ViewModel
import ru.potemkin.orpheusjetpackcompose.data.repositories.MessageRepositoryImpl

import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.GetMessageListUseCase

class ChatViewModel: ViewModel() {

    private val repository = MessageRepositoryImpl

    private val getMessageListUseCase = GetMessageListUseCase(repository)

    val messageList = getMessageListUseCase.getMessageList()

}