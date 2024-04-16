package ru.potemkin.orpheusjetpackcompose.presentation.chat.chatlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.ChatRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.GetChatListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.GetMyUserUseCase
import ru.potemkin.orpheusjetpackcompose.presentation.map.map.MapScreenState
import javax.inject.Inject

class ChatListViewModel @Inject constructor(
    private val getChatListUseCase: GetChatListUseCase,
    private val getMyUserUseCase: GetMyUserUseCase
) : ViewModel() {
    val myUser = getMyUserUseCase.invoke()

    val chatFlow = getChatListUseCase.invoke()

    val screenState = chatFlow
        .map { ChatListScreenState.Chats(chats = it.filter {
            myUser in it.users
        }) as ChatListScreenState }
        .onStart { emit(ChatListScreenState.Loading) }

}