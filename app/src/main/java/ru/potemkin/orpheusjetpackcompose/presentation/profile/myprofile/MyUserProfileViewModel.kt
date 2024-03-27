package ru.potemkin.orpheusjetpackcompose.presentation.profile.myprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.UserRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorType
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticType
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetUserPostsUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.GetMyUserUseCase
import javax.inject.Inject

class MyUserProfileViewModel @Inject constructor(
    private val getMyUserUseCase: GetMyUserUseCase,
    private val getMyUserPostsUseCase: GetUserPostsUseCase
) : ViewModel() {

    private val initialState = MyUserProfileScreenState.Initial

    private val _screenState = MutableLiveData<MyUserProfileScreenState>(initialState)
    val screenState: LiveData<MyUserProfileScreenState> = _screenState



    val myUser = getMyUserUseCase.invoke()
    val postList = getMyUserPostsUseCase.invoke(myUser.id)
    init {
        _screenState.value = MyUserProfileScreenState.Loading
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
//            val users = repository.loadUsers()
//            val user = repository.getMyUser()
//            val feedPosts = repository.loadPosts(user.id)

            _screenState.value = MyUserProfileScreenState.User(user = myUser,posts = postList)
        }
    }


}