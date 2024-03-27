package ru.potemkin.orpheusjetpackcompose.presentation.profile.otherusers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.UserRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorType
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticType
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.domain.usecases.band_usecases.GetBandUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.band_usecases.GetUserBandsUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.location_usecases.GetUserLocationUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetUserPostsUseCase
import javax.inject.Inject

class UserProfileViewModel @Inject constructor(
    userItem: UserItem,
    getUserPostsUseCase: GetUserPostsUseCase,
    getUserBandsUseCase: GetUserBandsUseCase,
    getUserLocationUseCase: GetUserLocationUseCase
    ) : ViewModel() {

    private val initialState = UserProfileScreenState.Initial

    private val _screenState = MutableLiveData<UserProfileScreenState>(initialState)
    val screenState: LiveData<UserProfileScreenState> = _screenState

    init {
        _screenState.value = UserProfileScreenState.Loading
        loadPosts(
            userItem,
            getUserPostsUseCase,
            getUserBandsUseCase,
            getUserLocationUseCase
        )
    }

    private fun loadPosts(user:UserItem,
                          getUserPostsUseCase: GetUserPostsUseCase,
                          getUserBandsUseCase: GetUserBandsUseCase,
                          getUserLocationUseCase: GetUserLocationUseCase) {
        viewModelScope.launch {
//            val feedPosts = repository.loadPosts(user.id)
            val feedPosts = getUserPostsUseCase.invoke(user.id)
            if(user.user_type == UserType.MUSICIAN) {
                _screenState.value = UserProfileScreenState.User(
                    user = user,
                    posts = feedPosts,
                    location = null,
                    bands = getUserBandsUseCase.invoke(user.id)
                )
            }
            else{
                _screenState.value = UserProfileScreenState.User(
                    user = user,
                    posts = feedPosts,
                    location = getUserLocationUseCase.invoke(user.id),
                    bands = null
                )
            }
        }
    }





}