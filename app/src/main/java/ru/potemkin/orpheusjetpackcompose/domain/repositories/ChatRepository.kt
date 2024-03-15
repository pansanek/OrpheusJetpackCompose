package ru.potemkin.orpheusjetpackcompose.domain.repositories

import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem

interface ChatRepository {
    fun addChatItem(chatItem: ChatItem)
    fun editChatItem(chatItem: ChatItem)
    fun getChatItem(chatId: String): ChatItem
    fun getChatList(userId: String): List<ChatItem>
    fun deleteChatItem(chatItem: ChatItem)
    fun addMessageItem(messageItem: MessageItem)
    fun deleteMessageItem(messageItem: MessageItem)
    fun editMessageItem(messageItem: MessageItem)
    fun getMessageList(chatId:String): List<MessageItem>
    fun getMessageItem(messageId: String): MessageItem

}