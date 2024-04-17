package ru.potemkin.orpheusjetpackcompose.presentation.profile.otherusers

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.NotificationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.NotificationType
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.usecases.band_usecases.GetBandListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.AddChatItemUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.AddMessageUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.EditChatItemUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.GetChatListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.GetMessageListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.location_usecases.GetLocationListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.notification_usecases.AddNotificationUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.notification_usecases.GetAllNotificationListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetPostListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.GetMyUserUseCase
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class UserProfileViewModel @Inject constructor(
    userItem: UserItem,

    private val addNotificationUseCase: AddNotificationUseCase,
    private val getAllNotificationListUseCase: GetAllNotificationListUseCase,
    private val getMyUserUseCase: GetMyUserUseCase,
    private val getChatListUseCase: GetChatListUseCase,
    private val addChatItemUseCase: AddChatItemUseCase,
    private val getMessageListUseCase: GetMessageListUseCase,
    private val addMessageUseCase: AddMessageUseCase,
    private val getPostListUseCase: GetPostListUseCase,
    private val getBandListUseCase: GetBandListUseCase,
    private val getLocationListUseCase: GetLocationListUseCase,
    private val editChatItemUseCase: EditChatItemUseCase
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("ViewModel", "Exception caught by exception handler")
    }


    val postListFlow = getPostListUseCase.invoke()

    val user = userItem

    val screenState = postListFlow
        .map { UserProfileScreenState.User(user = userItem,posts = it.filter { it.creatorId == userItem.id }) as UserProfileScreenState }
        .onStart { emit(UserProfileScreenState.Loading) }



    fun createChat(toUser: UserItem, message: String) {
        viewModelScope.launch(exceptionHandler) {
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
            val currentDate = sdf.format(Date())
            var chatExists = false
            var chatId = getNewChatId( getMyUserUseCase.invoke())
            for (i in getChatListUseCase.invoke().value) {
                if (toUser in i.users && getMyUserUseCase.invoke() in i.users) {
                    chatExists=true
                    addMessageUseCase.invoke(
                        MessageItem(
                            id = getNewMessageId(i.id),
                            chatId = i.id,
                            fromUser = getMyUserUseCase.invoke(),
                            timestamp = currentDate,
                            content = message
                        )
                    )
                    editChatItemUseCase.invoke(
                        ChatItem
                            (
                            id = i.id,
                            users = i.users,
                            lastMessage = message,
                            picture = i.picture,
                            name = i.name
                        )
                    )
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

    private fun getNewChatId(userItem: UserItem): String {
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

    fun getMyUserBands(): List<BandItem> {
        val flow = getBandListUseCase.invoke().value.filter {getMyUserUseCase.invoke() in it.members }
        return flow
    }

    fun getUserLocation():LocationItem{
        val flow = getLocationListUseCase.invoke().value.filter { it.admin == user }
        Log.d("GETUSERROFLS",flow.first().toString())
        return flow.first()
    }

    fun getUserBands():List<BandItem>{
        val list = getBandListUseCase.invoke().value.filter { user in it.members }
        Log.d("GETUSERROFLS",list.toString())
        return list
    }
    fun userInTheBand(bandItem: BandItem): Boolean {
        if (user in bandItem.members) return true
        return false
    }

}