package ru.potemkin.orpheusjetpackcompose.domain.repositories

import kotlinx.coroutines.flow.StateFlow
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem

interface ChatRepository {
    suspend fun addChatItem(chatItem: ChatItem)
    suspend fun editChatItem(chatItem: ChatItem)
    fun getChatItem(chatId: String): ChatItem
    fun getChatList(): StateFlow<List<ChatItem>>
    suspend fun deleteChatItem(chatItem: ChatItem)
    suspend fun addMessageItem(messageItem: MessageItem)
    suspend fun deleteMessageItem(messageItem: MessageItem)
    suspend fun editMessageItem(messageItem: MessageItem)
    fun getMessageList(): StateFlow<List<MessageItem>>
    fun getMessageItem(messageId: String): MessageItem

}
