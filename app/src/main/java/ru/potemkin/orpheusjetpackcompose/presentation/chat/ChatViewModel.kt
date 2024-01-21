package ru.potemkin.orpheusjetpackcompose.presentation.chat

import android.app.Application
import androidx.lifecycle.ViewModel
import ru.potemkin.orpheusjetpackcompose.data.repositories.MessageRepositoryImpl

import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.GetMessageListUseCase
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    private val getMessageListUseCase :GetMessageListUseCase
): ViewModel() {

    val messageList = getMessageListUseCase.getMessageList()

}