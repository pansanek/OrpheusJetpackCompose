package ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.news

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.PostRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorInfoItem
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
import ru.potemkin.orpheusjetpackcompose.domain.usecases.band_usecases.AddBandMemberUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.band_usecases.GetBandUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.location_usecases.GetLocationUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.notification_usecases.GetNotificationListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.AddPostUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.ChangeLikeStatusUseCase
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
    private val getNotificationListUseCase: GetNotificationListUseCase,
    private val changeLikeStatusUseCase: ChangeLikeStatusUseCase,
    private val addBandMemberUseCase: AddBandMemberUseCase
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("ViewModel", "Exception caught by exception handler")
    }



    val postListFlow = getPostListUseCase.invoke()

    val notificationListFlow = getNotificationListUseCase.invoke(getMyUserUseCase.invoke())

    val screenState = postListFlow
        .combine(notificationListFlow) { posts, notifications ->
            NewsFeedScreenState.Posts(posts = posts, notifications = notifications)
        }
        .filter { it.posts.isNotEmpty() } // Фильтруем, чтобы убедиться, что у нас есть посты
        .map { it as NewsFeedScreenState } // Преобразуем к типу NewsFeedScreenState
        .onStart { emit(NewsFeedScreenState.Loading) }


    private val _likeStatusMap = mutableMapOf<String, MutableStateFlow<Boolean>>()




//    private fun loadRecommendations() {
//        viewModelScope.launch {
////            val feedPosts = repository.loadRecommendations()
////            val feedPosts = addMockData()
//            _screenState.value = NewsFeedScreenState.Posts(posts = postList, notifications = notificationList)
//        }
//    }

    fun loadLocationFromCreator(locationId:String): LocationItem {
        return getLocationUseCase.invoke(locationId)
    }

    fun loadUserFromCreator(userId:String): UserItem {
        return getUserItemUseCase.invoke(userId)
    }
    fun loadBandFromCreator(bandId:String): BandItem {
        return getBandUseCase.invoke(bandId)

    }

    fun getCreatorInfo():CreatorInfoItem{

        return CreatorInfoItem(
            creatorId =  getMyUserUseCase.invoke().id,
            creatorName = getMyUserUseCase.invoke().name,
            creatorPicture = getMyUserUseCase.invoke().profile_picture,
            creatorType = CreatorType.USER
        )
    }

    fun changeLikeStatus(postId:String) {
        viewModelScope.launch(exceptionHandler) {
            val currentState = _likeStatusMap.getOrPut(postId) { MutableStateFlow(false) }.value
            _likeStatusMap[postId]?.value = !currentState
            changeLikeStatusUseCase.invoke(postId)
        }
    }
    fun getLikeStatus(postId: String): StateFlow<Boolean> {
        return _likeStatusMap.getOrPut(postId) { MutableStateFlow(false) }
    }

    fun acceptBandInvitation(bandItem: BandItem){
        viewModelScope.launch(exceptionHandler) {
            addBandMemberUseCase.invoke(bandItem, getMyUserUseCase.invoke())
        }
    }


}