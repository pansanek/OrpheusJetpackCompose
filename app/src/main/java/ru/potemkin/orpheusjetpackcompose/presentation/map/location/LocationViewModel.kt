package ru.potemkin.orpheusjetpackcompose.presentation.map.location

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.LocationRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorType

import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MusicianItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticType
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.AddChatItemUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.AddMessageUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.GetChatListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.GetMessageListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.location_usecases.EditLocationUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.EditPostUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetLocationPostsUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetUserPostsUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.GetMyUserUseCase
import ru.potemkin.orpheusjetpackcompose.presentation.map.map.MapScreenState
import ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.news.NewsFeedScreenState
import ru.potemkin.orpheusjetpackcompose.presentation.profile.otherusers.UserProfileScreenState
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject


class LocationViewModel @Inject constructor(
    location: LocationItem,
    getLocationPostsUseCase: GetLocationPostsUseCase,
    private val editLocationUseCase: EditLocationUseCase,
    private val editPostUseCase: EditPostUseCase,
    private val getMyUserUseCase: GetMyUserUseCase,
    private val getChatListUseCase: GetChatListUseCase,
    private val addChatItemUseCase: AddChatItemUseCase,
    private val getMessageListUseCase: GetMessageListUseCase,
    private val addMessageUseCase: AddMessageUseCase
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("ViewModel", "Exception caught by exception handler")
    }

    val postListFlow = getLocationPostsUseCase.invoke(location.id)
    val admin = location.admin


    val screenState = postListFlow
        .filter { it.isNotEmpty() }
        .map { LocationScreenState.Location(location,it) as LocationScreenState }
        .onStart { emit(LocationScreenState.Loading) }

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
    fun isMyUserAdmin():Boolean{
        if(getMyUserUseCase.invoke() == admin) return true
        return false
    }

    fun changeLocationProfile(
        oldProfile: LocationItem,
        locationName: String,
        locationAddress: String,
        locationAbout: String,
        profilePictureUrl: String
    ) {
        viewModelScope.launch(exceptionHandler) {
            val newLocation = LocationItem(
                id = oldProfile.id,
                admin = oldProfile.admin,
                name = locationName,
                address = locationAddress,
                about = locationAbout,
                profilePicture = PhotoUrlItem(
                    id = oldProfile.id,
                    url = profilePictureUrl
                ),
                latitude = oldProfile.latitude,
                longitude = oldProfile.longitude,
            )
            editLocationUseCase.invoke(newLocation)

            changeLocationPosts(locationName, profilePictureUrl)
        }

    }

    private fun changeLocationPosts(userName: String, profilePictureUrl: String) {
        viewModelScope.launch(exceptionHandler) {
            for (i in postListFlow.value) {
                i.creatorName = userName
                i.creatorPicture.url = profilePictureUrl
                editPostUseCase.invoke(i)
            }
        }

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

}