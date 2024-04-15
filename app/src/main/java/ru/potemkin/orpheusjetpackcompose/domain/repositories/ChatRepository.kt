package ru.potemkin.orpheusjetpackcompose.domain.repositories

import kotlinx.coroutines.flow.StateFlow
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem

interface ChatRepository {
    suspend fun addChatItem(chatItem: ChatItem)
    suspend fun editChatItem(chatItem: ChatItem)
    suspend fun getChatItem(chatId: String): ChatItem
    fun getChatList(userId: String): StateFlow<List<ChatItem>>
    suspend fun deleteChatItem(chatItem: ChatItem)
    suspend fun addMessageItem(messageItem: MessageItem)
    suspend fun deleteMessageItem(messageItem: MessageItem)
    suspend fun editMessageItem(messageItem: MessageItem)
    fun getMessageList(chatId:String): StateFlow<List<MessageItem>>
    suspend fun getMessageItem(messageId: String): MessageItem

}
