package ru.potemkin.orpheusjetpackcompose.domain.repositories

import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem

interface ChatRepository {
    fun addChatItem(chatItem: ChatItem)

    fun editChatItem(chatItem: ChatItem)

    fun getChatItem(chatId: String): ChatItem

    fun getChatsList(): List<ChatItem>

    suspend fun getMessageList(chatId:String): List<MessageItem>
}