package ru.potemkin.orpheusjetpackcompose.presentation.chat.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.ChatRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem

import javax.inject.Inject

class ChatViewModel @Inject constructor(chatItem: ChatItem) : ViewModel() {

    private val initialState = ChatScreenState.Initial

    private val _screenState = MutableLiveData<ChatScreenState>(initialState)
    val screenState: LiveData<ChatScreenState> = _screenState

    private val repository = ChatRepositoryImpl()
    init {
        _screenState.value = ChatScreenState.Loading
        loadMessages(chatItem.id)
    }
    private fun loadMessages(chatId:String){
        viewModelScope.launch {
            val messages = repository.getMessageList(chatId)
            _screenState.value = ChatScreenState.Messages(chatId = chatId, messages = messages)
        }
    }
}