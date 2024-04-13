package ru.potemkin.orpheusjetpackcompose.presentation.chat.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
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
import java.text.SimpleDateFormat
import java.util.Date

import javax.inject.Inject

class ChatViewModel @Inject constructor(
    private val chatItem: ChatItem,
    private val getMessageListUseCase: GetMessageListUseCase,
    private val addMessageUseCase: AddMessageUseCase,
    private val getMyUserUseCase: GetMyUserUseCase,
    private val editChatItemUseCase: EditChatItemUseCase
) : ViewModel() {

    private val initialState = ChatScreenState.Initial

    private val _screenState = MutableLiveData<ChatScreenState>(initialState)
    val screenState: LiveData<ChatScreenState> = _screenState

    private val repository = ChatRepositoryImpl()

    val messages = getMessageListUseCase.invoke(chatItem.id)

    val messagesFlow = MutableStateFlow(messages)

    init {
        _screenState.value = ChatScreenState.Loading
        loadMessages(chatItem.id)
    }

    private fun loadMessages(chatId: String) {
        viewModelScope.launch {
            _screenState.value = ChatScreenState.Messages(
                chatId = chatId,
                messages = messagesFlow.value
            )
        }
    }

    fun sendMessage(message: String) {
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
        messagesFlow.value = getMessageListUseCase.invoke(chatItem.id)
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

}