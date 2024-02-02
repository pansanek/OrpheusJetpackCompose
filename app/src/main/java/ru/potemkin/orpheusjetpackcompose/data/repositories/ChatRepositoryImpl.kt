package ru.potemkin.orpheusjetpackcompose.data.repositories

import android.app.Application
import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import ru.potemkin.orpheusjetpackcompose.R
import ru.potemkin.orpheusjetpackcompose.data.mappers.ChatMapper
import ru.potemkin.orpheusjetpackcompose.data.mappers.MessageMapper
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory
import ru.potemkin.orpheusjetpackcompose.data.network.ChatApiService
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.ChatRepository
import ru.potemkin.orpheusjetpackcompose.extentions.mergeWith
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

    override fun getChatsList(): List<ChatItem> = _chatItems
    override suspend fun getMessageList(chatId:String): List<MessageItem> {
        return messageMapper.mapMessageList(messageApiService.getChatMessages(chatId))
    }


}