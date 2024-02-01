package ru.potemkin.orpheusjetpackcompose.presentation.chat

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.mappers.UsersMapper
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory
import ru.potemkin.orpheusjetpackcompose.data.repositories.ChatRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.data.repositories.PostRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.AddUserUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.GetUserListUseCase
import ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.news.NewsFeedScreenState
import javax.inject.Inject

class ChatListViewModel (application: Application) : AndroidViewModel(application) {

    private val initialState = ChatListScreenState.Initial

    private val _screenState = MutableLiveData<ChatListScreenState>(initialState)
    val screenState: LiveData<ChatListScreenState> = _screenState

    private val repository = ChatRepositoryImpl(application)

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