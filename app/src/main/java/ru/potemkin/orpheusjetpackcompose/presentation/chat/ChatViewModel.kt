package ru.potemkin.orpheusjetpackcompose.presentation.chat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.ChatRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.data.repositories.MessageRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem

import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.GetMessageListUseCase
import javax.inject.Inject

class ChatViewModel(chatItem: ChatItem, application: Application) : AndroidViewModel(application) {

    private val initialState = ChatScreenState.Initial

    private val _screenState = MutableLiveData<ChatScreenState>(initialState)
    val screenState: LiveData<ChatScreenState> = _screenState

    private val repository = ChatRepositoryImpl(application)
    init {
        _screenState.value = ChatScreenState.Loading
        loadMessages(chatItem.id)
    }
    private fun loadMessages(chatId:String){
        viewModelScope.launch {
            val messages = repository.getMessageList(chatId)
            _screenState.value = ChatScreenState.Messages(chatId=chatId,messages = messages)
        }
    }
}