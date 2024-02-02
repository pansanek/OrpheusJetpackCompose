package ru.potemkin.orpheusjetpackcompose.presentation.chat.chatlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.ChatRepositoryImpl
import javax.inject.Inject

class ChatListViewModel @Inject constructor() : ViewModel() {

    private val initialState = ChatListScreenState.Initial

    private val _screenState = MutableLiveData<ChatListScreenState>(initialState)
    val screenState: LiveData<ChatListScreenState> = _screenState

    private val repository = ChatRepositoryImpl()

    init {
        _screenState.value = ChatListScreenState.Loading
        loadChats()
    }
    private fun loadChats(){
        viewModelScope.launch {
            val chats = repository.loadChats()
            _screenState.value = ChatListScreenState.Chats(chats = chats)
        }
    }

}