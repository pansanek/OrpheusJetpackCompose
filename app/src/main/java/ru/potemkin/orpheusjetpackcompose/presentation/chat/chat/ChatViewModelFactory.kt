package ru.potemkin.orpheusjetpackcompose.presentation.chat.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.GetMessageListUseCase

class ChatViewModelFactory(
    private val chatItem: ChatItem,
    private val getMessageListUseCase: GetMessageListUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChatViewModel(chatItem,getMessageListUseCase) as T
    }
}