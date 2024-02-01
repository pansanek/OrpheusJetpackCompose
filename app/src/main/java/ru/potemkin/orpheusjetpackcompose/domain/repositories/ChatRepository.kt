package ru.potemkin.orpheusjetpackcompose.domain.repositories

import kotlinx.coroutines.flow.StateFlow
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem

interface ChatRepository {
    fun addChatItem(chatItem: ChatItem)

    fun editChatItem(chatItem: ChatItem)

    fun getChatItem(chatId: String): ChatItem

    fun getChatsList(): List<ChatItem>

    suspend fun getMessageList(chatId:String): List<MessageItem>
}