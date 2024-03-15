package ru.potemkin.orpheusjetpackcompose.data.repositories

import ru.potemkin.orpheusjetpackcompose.data.mappers.ChatMapper
import ru.potemkin.orpheusjetpackcompose.data.mappers.MessageMapper
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.NotificationItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.ChatRepository
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(

): ChatRepository {

    private val apiService = ApiFactory.appChatApiService
    private val messageApiService = ApiFactory.appMessageApiService
    private val mapper = ChatMapper()
    private val messageMapper = MessageMapper()

    private val _chatItems = mutableListOf<ChatItem>()
    private val chatItems: List<ChatItem>
        get() = _chatItems.toList()

    private val _messageItems = mutableListOf<MessageItem>()
    private val messageItems: List<MessageItem>
        get() = _messageItems.toList()

    private var nextFrom: String? = null

    suspend fun loadChats(): List<ChatItem> {
        val startFrom = nextFrom

        if (startFrom == null && chatItems.isNotEmpty()) return chatItems

        val response = if (startFrom == null) {
            apiService.getAllChats()
        } else {
            apiService.getAllChats()
        }
        val chats = mapper.mapChatList(response)
        _chatItems.addAll(chats)
        return chatItems
    }

    override fun addChatItem(chatItem: ChatItem) {
        _chatItems.add(chatItem)
    }

    override fun editChatItem(chatItem: ChatItem) {
        val oldElement = getChatItem(chatItem.id)
        _chatItems.remove(oldElement)
        addChatItem(chatItem)
    }

    override fun getChatItem(chatId: String): ChatItem {
        return chatItems.find {
            it.id == chatId
        } ?: throw java.lang.RuntimeException("Element with id $chatId not found")
    }

    override fun getChatList(userId: String): List<ChatItem> {
        return _chatItems.toList()
    }


    override fun deleteChatItem(chatItem: ChatItem) {
        _chatItems.remove(chatItem)
    }

    override fun addMessageItem(messageItem: MessageItem) {
        _messageItems.add(messageItem)
    }

    override fun deleteMessageItem(messageItem: MessageItem) {
        _messageItems.remove(messageItem)
    }
    override fun getMessageItem(messageId: String): MessageItem {
        return _messageItems.find {
            it.id == messageId
        } ?: throw java.lang.RuntimeException("Element with id $messageId not found")
    }
    override fun editMessageItem(messageItem: MessageItem) {
        val oldElement = getMessageItem(messageItem.id)
        _messageItems.remove(oldElement)
        addMessageItem(messageItem)
    }

    override fun getMessageList(chatId: String): List<MessageItem> {
        val chatMessage = mutableListOf<MessageItem>()
        for (message in _messageItems){
            if(message.chatId == chatId) chatMessage.add(message)
        }
        return chatMessage
    }


}