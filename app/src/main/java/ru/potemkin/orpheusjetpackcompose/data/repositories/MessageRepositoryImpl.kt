package ru.potemkin.orpheusjetpackcompose.data.repositories

import ru.potemkin.orpheusjetpackcompose.data.mappers.MessageMapper
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory
import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.MessageRepository
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(

): MessageRepository {

    private val apiService = ApiFactory.appMessageApiService
    private val messageApiService = ApiFactory.appMessageApiService
    private val mapper = MessageMapper()
    private val messageMapper = MessageMapper()

    private val _messageItems = mutableListOf<MessageItem>()
    private val messageItems: List<MessageItem>
        get() = _messageItems.toList()

    private var nextFrom: String? = null

    suspend fun loadMessages(): List<MessageItem> {
        val startFrom = nextFrom

        if (startFrom == null && messageItems.isNotEmpty()) return messageItems

        val response = if (startFrom == null) {
            apiService.getAllMessages()
        } else {
            apiService.getAllMessages()
        }
        val messages = mapper.mapMessageList(response)
        _messageItems.addAll(messages)
        return messageItems
    }

    override fun addMessageItem(messageItem: MessageItem) {
        _messageItems.add(messageItem)
    }

    override fun deleteMessageItem(messageItem: MessageItem) {
        _messageItems.remove(messageItem)
    }

    override fun editMessageItem(messageItem: MessageItem) {
        val oldElement = getMessageItem(messageItem.id)
        _messageItems.remove(oldElement)
        addMessageItem(messageItem)
    }

    override fun getMessageItem(messageId: String): MessageItem {
        return messageItems.find {
            it.id == messageId
        } ?: throw java.lang.RuntimeException("Element with id $messageId not found")
    }

    override fun getMessagesList(): List<MessageItem> = _messageItems



}