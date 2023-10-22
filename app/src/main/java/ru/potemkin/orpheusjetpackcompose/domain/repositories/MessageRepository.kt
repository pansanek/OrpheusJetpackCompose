package ru.potemkin.orpheusjetpackcompose.domain.repositories

import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem

interface MessageRepository {
    fun addMessageItem(messageItem: MessageItem)

    fun deleteMessageItem(messageItem: MessageItem)

    fun editMessageItem(messageItem: MessageItem)

    fun getMessageItem(messageId: Int): MessageItem

    fun getMessagesList(): List<MessageItem>
}