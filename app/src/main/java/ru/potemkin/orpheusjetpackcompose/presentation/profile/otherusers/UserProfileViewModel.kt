package ru.potemkin.orpheusjetpackcompose.presentation.profile.otherusers

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.NotificationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.NotificationType
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.usecases.band_usecases.GetMyUserBandsUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.band_usecases.GetUserBandsUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.AddChatItemUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.AddMessageUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.GetChatListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.GetMessageListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.location_usecases.GetUserLocationUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.notification_usecases.AddNotificationUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.notification_usecases.GetAllNotificationListUseCase
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

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("ViewModel", "Exception caught by exception handler")
    }


    val postListFlow = getUserPostsUseCase.invoke(userItem.id)

    val bandListFlow = getUserBandsUseCase.invoke(userItem.id)
    val location = getUserLocationUseCase.invoke(userItem.id)

    val screenState = postListFlow
        .combine(bandListFlow) { posts, bands ->
            UserProfileScreenState.User(user = userItem, bands = bands, location = location, posts = posts)
        }
        .filter { it.posts.isNotEmpty() } // Фильтруем, чтобы убедиться, что у нас есть посты
        .map { it as UserProfileScreenState } // Преобразуем к типу UserProfileScreenState
        .onStart { emit(UserProfileScreenState.Loading) }



    fun createChat(toUser: UserItem, message: String) {
        viewModelScope.launch(exceptionHandler) {
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
            val currentDate = sdf.format(Date())
            var chatExists = false
            var chatId = getNewChatId(getMyUserUseCase.invoke())
            for (i in getChatListUseCase.invoke(getMyUserUseCase.invoke().id).value) {
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
    }

    fun sendBandInvitation(bandItem: BandItem, user: UserItem) {
        viewModelScope.launch(exceptionHandler) {
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

    }

    private fun getNewNotificationId(): String {

        var notificationList = getAllNotificationListUseCase.invoke()
        val indexes: MutableList<String> = ArrayList()
        var largest: Int = 0
        for (i in notificationList.value) {
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

    private fun getNewChatId(userItem: UserItem): String {
        var chatList = getChatListUseCase.invoke(userItem.id)
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

    fun getMyUserBands(): List<BandItem> {
        return getMyUserBandsUseCase.invoke(getMyUserUseCase.invoke().id).value
    }


}