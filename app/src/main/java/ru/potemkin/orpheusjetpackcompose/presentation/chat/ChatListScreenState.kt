package ru.potemkin.orpheusjetpackcompose.presentation.chat

import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem


sealed class ChatListScreenState {

    object Initial : ChatListScreenState()
    object Loading : ChatListScreenState()
    data class Chats(
        val chats: List<ChatItem>
    ) : ChatListScreenState()
}