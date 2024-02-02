package ru.potemkin.orpheusjetpackcompose.presentation.chat.chat

import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem


sealed class ChatScreenState {

    object Initial : ChatScreenState()
    object Loading : ChatScreenState()
    data class Messages(
        val chatId: String,
        val messages: List<MessageItem>
    ) : ChatScreenState()
}