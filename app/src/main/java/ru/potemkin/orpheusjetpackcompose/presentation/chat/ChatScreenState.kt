package ru.potemkin.orpheusjetpackcompose.presentation.chat

import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem


sealed class ChatScreenState {

    object Initial : ChatScreenState()
    object Loading : ChatScreenState()
    data class Messages(
        val chatId: String,
        val messages: List<MessageItem>
    ) : ChatScreenState()
}