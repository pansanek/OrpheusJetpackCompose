package ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.PostRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorType
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.NotificationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.NotificationType
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticType
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.domain.usecases.band_usecases.GetBandUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.location_usecases.GetLocationUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.notification_usecases.GetNotificationListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.AddPostUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetCommentsUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetPostListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.GetMyUserUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.GetUserUseCase
import javax.inject.Inject

class NewsFeedViewModel @Inject constructor(
    private val getPostListUseCase: GetPostListUseCase,
    private val addPostUseCase: AddPostUseCase,
    private val getCommentsUseCase: GetCommentsUseCase,
    private val getUserItemUseCase: GetUserUseCase,
    private val getMyUserUseCase: GetMyUserUseCase,
    private val getLocationUseCase: GetLocationUseCase,
    private val getBandUseCase: GetBandUseCase,
    private val getNotificationListUseCase: GetNotificationListUseCase
) : ViewModel() {

    private val initialState = NewsFeedScreenState.Initial

    private val _screenState = MutableLiveData<NewsFeedScreenState>(initialState)
    val screenState: LiveData<NewsFeedScreenState> = _screenState


    val postList = getPostListUseCase.invoke()
    val notificationList = getNotificationListUseCase.invoke(getMyUserUseCase.invoke())

    init {
        _screenState.value = NewsFeedScreenState.Loading
        loadRecommendations()
    }

    private fun loadRecommendations() {
        viewModelScope.launch {
//            val feedPosts = repository.loadRecommendations()
//            val feedPosts = addMockData()
            _screenState.value = NewsFeedScreenState.Posts(posts = postList, notifications = notificationList)
        }
    }

    fun loadLocationFromCreator(locationId:String): LocationItem {
        return getLocationUseCase.invoke(locationId)
    }

    fun loadUserFromCreator(userId:String): UserItem {
        return getUserItemUseCase.invoke(userId)
    }
    fun loadBandFromCreator(bandId:String): BandItem {
        return getBandUseCase.invoke(bandId)

    }


//    fun changeLikeStatus(feedPost: PostItem) {
//        viewModelScope.launch {
//            repository.changeLikeStatus(feedPost)
//            _screenState.value = NewsFeedScreenState.Posts(posts = repository.postList)
//        }
//    }

}