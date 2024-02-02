package ru.potemkin.orpheusjetpackcompose.presentation.profile.myprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.UserRepositoryImpl
import javax.inject.Inject

class MyUserProfileViewModel @Inject constructor() : ViewModel() {

    private val initialState = MyUserProfileScreenState.Initial

    private val _screenState = MutableLiveData<MyUserProfileScreenState>(initialState)
    val screenState: LiveData<MyUserProfileScreenState> = _screenState

    private val repository = UserRepositoryImpl()

    init {
        _screenState.value = MyUserProfileScreenState.Loading
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
            val users = repository.loadUsers()
            val user = repository.getMyUser()
            val feedPosts = repository.loadPosts(user.id)

            _screenState.value = MyUserProfileScreenState.User(user = user,posts = feedPosts)
        }
    }





}