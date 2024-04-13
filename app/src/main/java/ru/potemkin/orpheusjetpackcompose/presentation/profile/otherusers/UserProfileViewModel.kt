package ru.potemkin.orpheusjetpackcompose.presentation.profile.otherusers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.UserRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorType
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
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
import ru.potemkin.orpheusjetpackcompose.domain.usecases.band_usecases.GetMyUserBandsUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.band_usecases.GetUserBandsUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.AddChatItemUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.AddMessageUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.GetChatListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.GetMessageListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.location_usecases.GetUserLocationUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.notification_usecases.AddNotificationUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.notification_usecases.GetAllNotificationListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.notification_usecases.GetNotificationListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetUserPostsUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.GetMyUserUseCase
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class UserProfileViewModel @Inject constructor(
    userItem: UserItem,
    getUserPostsUseCase: GetUserPostsUseCase,
    getUserBandsUseCase: GetUserBandsUseCase,
    getUserLocationUseCase: GetUserLocationUseCase,
    private val addNotificationUseCase: AddNotificationUseCase,
    private val getAllNotificationListUseCase: GetAllNotificationListUseCase,
    private val getMyUserUseCase: GetMyUserUseCase,
    private val getMyUserBandsUseCase: GetMyUserBandsUseCase,
    private val getChatListUseCase: GetChatListUseCase,
    private val addChatItemUseCase: AddChatItemUseCase,
    private val getMessageListUseCase: GetMessageListUseCase,
    private val addMessageUseCase: AddMessageUseCase
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

    private fun loadPosts(
        user: UserItem,
        getUserPostsUseCase: GetUserPostsUseCase,
        getUserBandsUseCase: GetUserBandsUseCase,
        getUserLocationUseCase: GetUserLocationUseCase
    ) {
        viewModelScope.launch {
//            val feedPosts = repository.loadPosts(user.id)
            val feedPosts = getUserPostsUseCase.invoke(user.id)
            if (user.user_type == UserType.MUSICIAN) {
                _screenState.value = UserProfileScreenState.User(
                    user = user,
                    posts = feedPosts,
                    location = null,
                    bands = getUserBandsUseCase.invoke(user.id)
                )
            } else {
                _screenState.value = UserProfileScreenState.User(
                    user = user,
                    posts = feedPosts,
                    location = getUserLocationUseCase.invoke(user.id),
                    bands = null
                )
            }
        }
    }

    fun createChat(toUser: UserItem, message: String) {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
        val currentDate = sdf.format(Date())
        var chatExists = false
        var chatId = getNewChatId(getMyUserUseCase.invoke())
        for (i in getChatListUseCase.invoke(getMyUserUseCase.invoke().id)) {
            if (toUser in i.users) {
                chatId = i.id
                chatExists = true
            }
        }
        if (!chatExists) {
            addChatItemUseCase.invoke(
                ChatItem(
                    id = chatId,
                    users = listOf(getMyUserUseCase.invoke(), toUser),
                    lastMessage = message,
                    picture = toUser.profile_picture,
                    name = toUser.name
                )
            )
        }
        addMessageUseCase.invoke(
            MessageItem(
                id = getNewMessageId(chatId),
                chatId = chatId,
                fromUser = getMyUserUseCase.invoke(),
                timestamp = currentDate,
                content = message
            )
        )
    }

    fun sendBandInvitation(bandItem: BandItem, user: UserItem) {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
        val currentDate = sdf.format(Date())
        addNotificationUseCase.invoke(
            NotificationItem(
                id = getNewNotificationId(),
                type = NotificationType.BAND_INVITE,
                contentDescription = " пригласил вас в группу ",
                title = "Вас пригласили в группу",
                toUser = user,
                fromUser = getMyUserUseCase.invoke(),
                postItem = null,
                date = currentDate,
                bandItem = bandItem
            )
        )

    }

    private fun getNewNotificationId(): String {
        var notificationList = getAllNotificationListUseCase.invoke()
        val indexes: MutableList<String> = ArrayList()
        var largest: Int = 0
        for (i in notificationList) {
            indexes.add(i.id)
        }
        for (i in indexes) {
            if (largest < i.toIntOrNull()!!)
                largest = i.toIntOrNull()!!
        }
        largest = largest + 1
        return largest.toString()
    }

    private fun getNewMessageId(chatId: String): String {
        var messageList = getMessageListUseCase.invoke(chatId)
        val indexes: MutableList<String> = ArrayList()
        var largest: Int = 0
        for (i in messageList) {
            indexes.add(i.id)
        }
        for (i in indexes) {
            if (largest < i.toIntOrNull()!!)
                largest = i.toIntOrNull()!!
        }
        largest = largest + 1
        return largest.toString()
    }

    private fun getNewChatId(userItem: UserItem): String {
        var chatList = getChatListUseCase.invoke(userItem.id)
        val indexes: MutableList<String> = ArrayList()
        var largest: Int = 0
        for (i in chatList) {
            indexes.add(i.id)
        }
        for (i in indexes) {
            if (largest < i.toIntOrNull()!!)
                largest = i.toIntOrNull()!!
        }
        largest = largest + 1
        return largest.toString()
    }

    fun getMyUserBands(): List<BandItem> {
        return getMyUserBandsUseCase.invoke(getMyUserUseCase.invoke().id)
    }


}