package ru.potemkin.orpheusjetpackcompose.presentation.chat.chatlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.ChatRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.GetChatListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.GetMyUserUseCase
import javax.inject.Inject

class ChatListViewModel @Inject constructor(
    private val getChatListUseCase: GetChatListUseCase,
    private val getMyUserUseCase: GetMyUserUseCase
) : ViewModel() {

    private val initialState = ChatListScreenState.Initial

    private val _screenState = MutableLiveData<ChatListScreenState>(initialState)
    val screenState: LiveData<ChatListScreenState> = _screenState

    private val repository = ChatRepositoryImpl()

    init {
        _screenState.value = ChatListScreenState.Loading
        loadChats()
    }

    private fun loadChats() {
        viewModelScope.launch {
//            val chats = repository.loadChats()
            _screenState.value = ChatListScreenState.Chats(
                chats = getChatListUseCase
                    .invoke(
                        getMyUserUseCase.invoke().id
                    )
            )
        }
    }



}