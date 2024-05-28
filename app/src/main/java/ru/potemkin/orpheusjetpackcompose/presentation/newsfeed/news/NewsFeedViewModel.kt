package ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.preferences.AuthPreferences
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorInfoItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorType
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.domain.usecases.band_usecases.AddBandMemberUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.band_usecases.GetBandListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.AddChatItemUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.AddMessageUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.GetChatListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.GetMessageListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.location_usecases.GetLocationListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.location_usecases.GetLocationUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.notification_usecases.GetNotificationListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.AddPostUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.ChangeLikeStatusUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetCommentsUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetPostListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.GetMyUserUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.GetUserByIdUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.GetUserListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.SetMyUserUseCase
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class NewsFeedViewModel @Inject constructor(
    private val getPostListUseCase: GetPostListUseCase,
    private val addPostUseCase: AddPostUseCase,
    private val getCommentsUseCase: GetCommentsUseCase,
    private val getUserListUseCase: GetUserListUseCase,
    private val getBandListUseCase: GetBandListUseCase,
    private val getMyUserUseCase: GetMyUserUseCase,
    private val getLocationListUseCase: GetLocationListUseCase,
    private val getNotificationListUseCase: GetNotificationListUseCase,
    private val changeLikeStatusUseCase: ChangeLikeStatusUseCase,
    private val addBandMemberUseCase: AddBandMemberUseCase,
    private val setMyUserUseCase: SetMyUserUseCase,
    private val authPreferences: AuthPreferences,
    private val getChatListUseCase: GetChatListUseCase,
    private val addChatItemUseCase: AddChatItemUseCase,
    private val getMessageListUseCase: GetMessageListUseCase,
    private val addMessageUseCase: AddMessageUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("ViewModel", "Exception caught by exception handler")
    }


    val postListFlow = getPostListUseCase.invoke()

    val notificationListFlow = getNotificationListUseCase.invoke()

    val screenState = postListFlow
        .combine(notificationListFlow) { posts, notifications ->
            NewsFeedScreenState.Posts(posts = posts, notifications = notifications.filter {
                it.toUser.id == getMyUserUseCase.invoke().id
            })
        }
        .map { it as NewsFeedScreenState } // Преобразуем к типу NewsFeedScreenState
        .onStart { emit(NewsFeedScreenState.Loading) }

    init {
        setMyUserUseCase.invoke(getUserByIdUseCase.invoke(authPreferences.getUserId()))
    }

    private val _likeStatusMap = mutableMapOf<String, MutableStateFlow<Boolean>>()


//    private fun loadRecommendations() {
//        viewModelScope.launch {
////            val feedPosts = repository.loadRecommendations()
////            val feedPosts = addMockData()
//            _screenState.value = NewsFeedScreenState.Posts(posts = postList, notifications = notificationList)
//        }
//    }

    suspend fun loadLocationFromCreator(locationId: String): LocationItem {
        val locationList = getLocationListUseCase.invoke()
            .map {it.filter{ location -> location.id == locationId }
            } .firstOrNull() ?: emptyList() //

        if (locationList.isNotEmpty()) {
            return locationList.first() // Return the first location if the list is not empty
        } else {
            // Handle the case where no matching location is found
            throw NoSuchElementException("Location with id $locationId not found")
        }
    }

    suspend fun loadUserFromCreator(userId: String): UserItem {
        val userList = getUserListUseCase.invoke()
            .map {it.filter{ user -> user.id == userId }
            } .firstOrNull() ?: emptyList() //

        if (userList.isNotEmpty()) {
            return userList.first() // Return the first user if the list is not empty
        } else {
            // Handle the case where no matching user is found
            throw NoSuchElementException("User with id $userId not found")
        }
    }

    suspend fun loadBandFromCreator(bandId: String): BandItem {
        val bandList = getBandListUseCase.invoke()
            .map { it.filter { band -> band.id == bandId } }
            .firstOrNull() ?: emptyList() // If flow is empty, return an empty list

        if (bandList.isNotEmpty()) {
            return bandList[0] // Returning the first item from the list
        } else {
            // Handle the case where no matching band is found
            throw NoSuchElementException("Band with id $bandId not found")
        }
    }

    fun getCreatorInfo(): CreatorInfoItem {
        return CreatorInfoItem(
            creatorId = getMyUserUseCase.invoke().id,
            creatorName = getMyUserUseCase.invoke().login,
            creatorPicture = getMyUserUseCase.invoke().profile_picture,
            creatorType = CreatorType.USER
        )
    }

    fun changeLikeStatus(postItem: PostItem) {
        viewModelScope.launch(exceptionHandler) {
            val currentState = _likeStatusMap.getOrPut(postItem.id) { MutableStateFlow(postItem.isLiked) }.value
            _likeStatusMap[postItem.id]?.value = !currentState
            changeLikeStatusUseCase.invoke(postItem.id,authPreferences.getUserId())
        }
    }

    fun getLikeStatus(postItem: PostItem): StateFlow<Boolean> {
        return _likeStatusMap.getOrPut(postItem.id) { MutableStateFlow(postItem.isLiked) }
    }

    fun acceptBandInvitation(bandItem: BandItem) {
        viewModelScope.launch(exceptionHandler) {
            addBandMemberUseCase.invoke(bandItem, getMyUserUseCase.invoke())
            bandChat(bandItem)
        }
    }

    /*
    Для видоса начало
     */
    fun bandChat(bandItem: BandItem) {
        viewModelScope.launch(exceptionHandler) {
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
            val bandItem = bandItem
            val members = bandItem.members
            val currentDate = sdf.format(Date())
            var chatExists = false
            var chatId = getNewChatId()
            for (i in getChatListUseCase.invoke().value) {
                if (members == i.users && getMyUserUseCase.invoke() in i.users) {
                    chatId = i.id
                    chatExists = true
                }
            }
            if (!chatExists) {
                addChatItemUseCase.invoke(
                    ChatItem(
                        id = chatId,
                        users = members,
                        lastMessage = "Теперь надо репетицию устроить",
                        picture = bandItem.photo,
                        name = bandItem.name
                    )
                )
            }
            addMessageUseCase.invoke(
                MessageItem(
                    id = getNewMessageId(),
                    chatId = chatId,
                    fromUser = UserItem(
                        "19",
                        "landontewers",
                        "Лэндон Тьюерс",
                        "12341234",
                        "9@gmail.com",
                        "Много пою и кричу",
                        UserType.MUSICIAN,
                        PhotoUrlItem(
                            "199",
                            "https://sun9-25.userapi.com/impf/c840320/v840320259/36208/h5GVeRP9URM.jpg?size=640x640&quality=96&sign=f5307f49e081c58b7cbb3bbb4680efb6&c_uniq_tag=FbHPADgjU38jiYwFHjugwpbBeRJbDbBXfs4fCfTv3rk&type=album"
                        ),
                        PhotoUrlItem(
                            "1109",
                            "https://i0.wp.com/distortedsoundmag.com/wp-content/uploads/2017/11/TPIY_Dispose_3000px_600dpi_RGB.jpg?w=3000&ssl=1"
                        ),
                        UserSettingsItem(true, true)
                    ),
                    timestamp = currentDate,
                    content = "Теперь надо репетицию устроить"
                )
            )
        }
    }

    private fun getNewMessageId(): String {
        var messageList = getMessageListUseCase.invoke()
        val indexes: MutableList<String> = ArrayList()
        var largest: Int = 0
        for (i in messageList.value) {
            indexes.add(i.id)
        }
        for (i in indexes) {
            if (largest < i.toIntOrNull()!!)
                largest = i.toIntOrNull()!!
        }
        largest = largest + 1
        return largest.toString()
    }

    private fun getNewChatId(): String {
        var chatList = getChatListUseCase.invoke()
        val indexes: MutableList<String> = ArrayList()
        var largest: Int = 0
        for (i in chatList.value) {
            indexes.add(i.id)
        }
        for (i in indexes) {
            if (largest < i.toIntOrNull()!!)
                largest = i.toIntOrNull()!!
        }
        largest = largest + 1
        return largest.toString()
    }

    /*
    Для видоса конце
     */
    fun userInTheBand(bandItem: BandItem): Boolean {
        if (getMyUserUseCase.invoke() in bandItem.members) return true
        return false
    }

}