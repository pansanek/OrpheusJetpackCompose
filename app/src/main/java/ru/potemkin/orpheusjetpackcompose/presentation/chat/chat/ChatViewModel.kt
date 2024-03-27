package ru.potemkin.orpheusjetpackcompose.presentation.chat.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.ChatRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.GetMessageListUseCase

import javax.inject.Inject

class ChatViewModel @Inject constructor(
    chatItem: ChatItem,
    getMessageListUseCase: GetMessageListUseCase
    ) : ViewModel() {

    private val initialState = ChatScreenState.Initial

    private val _screenState = MutableLiveData<ChatScreenState>(initialState)
    val screenState: LiveData<ChatScreenState> = _screenState

    private val repository = ChatRepositoryImpl()

    init {
        _screenState.value = ChatScreenState.Loading
        loadMessages(chatItem.id,getMessageListUseCase)
    }

    private fun loadMessages(chatId: String,getMessageListUseCase: GetMessageListUseCase) {
        viewModelScope.launch {
//            val messages = repository.getMessageList(chatId)
            val messages = getMessageListUseCase.invoke(chatId)
            _screenState.value = ChatScreenState.Messages(chatId = chatId, messages = messages)
        }
    }

}