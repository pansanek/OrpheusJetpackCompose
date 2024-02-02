package ru.potemkin.orpheusjetpackcompose.presentation.profile.otherusers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.UserRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import javax.inject.Inject

class UserProfileViewModel @Inject constructor(userItem: UserItem) : ViewModel() {

    private val initialState = UserProfileScreenState.Initial

    private val _screenState = MutableLiveData<UserProfileScreenState>(initialState)
    val screenState: LiveData<UserProfileScreenState> = _screenState

    private val repository = UserRepositoryImpl()

    init {
        _screenState.value = UserProfileScreenState.Loading
        loadPosts(userItem)
    }

    private fun loadPosts(user:UserItem) {
        viewModelScope.launch {
            val feedPosts = repository.loadPosts(user.id)
            _screenState.value = UserProfileScreenState.User(user = user,posts = feedPosts)
        }
    }





}