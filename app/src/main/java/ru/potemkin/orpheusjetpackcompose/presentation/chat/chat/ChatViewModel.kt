package ru.potemkin.orpheusjetpackcompose.presentation.chat.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import ru.potemkin.orpheusjetpackcompose.data.mappers.MessageMapper
import ru.potemkin.orpheusjetpackcompose.data.model.PhotoUrlDto
import ru.potemkin.orpheusjetpackcompose.data.model.UserDto
import ru.potemkin.orpheusjetpackcompose.data.model.UserSettingsDto
import ru.potemkin.orpheusjetpackcompose.data.model.create_requests.CreateMessageRequest
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory
import ru.potemkin.orpheusjetpackcompose.data.repositories.ChatRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.AddMessageUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.EditChatItemUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.GetMessageListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.GetMyUserUseCase
import ru.potemkin.orpheusjetpackcompose.presentation.map.map.MapScreenState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID

import javax.inject.Inject

class ChatViewModel @Inject constructor(
    private val chatItem: ChatItem,
    private val getMessageListUseCase: GetMessageListUseCase,
    private val addMessageUseCase: AddMessageUseCase,
    private val getMyUserUseCase: GetMyUserUseCase,
    private val editChatItemUseCase: EditChatItemUseCase
) : ViewModel() {

    private val mapper = MessageMapper()

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("ViewModel", "Exception caught by exception handler")
    }

    init {
        loadMessages()
    }

    val messagesFlow = getMessageListUseCase.invoke()

    val screenState = messagesFlow
        .map { ChatScreenState.Messages(messages = it.filter {
            it.chatId == chatItem.id
        }, chatId = chatItem.id) as ChatScreenState }
        .onStart { emit(ChatScreenState.Loading) }

    fun sendMessage(message: String) {
        viewModelScope.launch(exceptionHandler) {
            Log.d("SENDMESSAGE", message)
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
            val currentDate = sdf.format(Date())
            addMessageUseCase.invoke(
                MessageItem(
                    id = getNewMessageId(),
                    chatId = chatItem.id,
                    fromUser = getMyUserUseCase.invoke(),
                    timestamp = currentDate,
                    content = message,
                )
            )
            editChatItemUseCase.invoke(
                ChatItem
                    (
                    id = chatItem.id,
                    users = chatItem.users,
                    lastMessage = message,
                    picture = chatItem.picture,
                    name = chatItem.name
                )
            )

        }
    }

    private fun getNewMessageId(): String {
        var messageList = messagesFlow.value
        val indexes: MutableList<String> = ArrayList()
        var largest: Int = 0
        for (i in messageList) {
            indexes.add(i.id)
        }
        for (i in indexes) {
            if (largest < i.toIntOrNull()!!)
                largest = i.toIntOrNull()!!
        }
        largest = largest + 1
        Log.d("CREATEMESSAGE", "ID" + largest.toString())
        return largest.toString()
    }

    fun getMyUserId(): String {
        return getMyUserUseCase.invoke().id
    }

    private fun loadMessages(){
        viewModelScope.launch {
            val messages = ApiFactory.appMessageApiService.getAllMessages()
            val messageItems = mapper.mapMessageList(messages)
            Log.d("MESSAGES",messageItems.toString())
        }
    }


}