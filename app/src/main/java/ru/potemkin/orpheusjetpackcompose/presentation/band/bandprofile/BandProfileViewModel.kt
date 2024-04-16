package ru.potemkin.orpheusjetpackcompose.presentation.band.bandprofile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.usecases.band_usecases.EditBandUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.AddChatItemUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.AddMessageUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.GetChatListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases.GetMessageListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.EditPostUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetPostListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.GetMyUserUseCase
import ru.potemkin.orpheusjetpackcompose.presentation.profile.myprofile.MyUserProfileScreenState
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class BandProfileViewModel @Inject constructor(
    bandItem: BandItem,
    private val editBandUseCase: EditBandUseCase,
    private val getMyUserUseCase: GetMyUserUseCase,
    private val editPostUseCase: EditPostUseCase,
    private val getChatListUseCase: GetChatListUseCase,
    private val addChatItemUseCase: AddChatItemUseCase,
    private val getMessageListUseCase: GetMessageListUseCase,
    private val addMessageUseCase: AddMessageUseCase,
    private val getPostListUseCase: GetPostListUseCase
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("ViewModel", "Exception caught by exception handler")
    }
    val bandItem = bandItem
    val members = bandItem.members
    val postListFlow = getPostListUseCase.invoke()

    val screenState = postListFlow
        .map { BandProfileScreenState.Band(band = bandItem, posts = it.filter {
            it.creatorId == bandItem.id
        }) as BandProfileScreenState }
        .onStart { emit(BandProfileScreenState.Loading) }

    fun isMyUserInBand(): Boolean {
        if (getMyUserUseCase.invoke() in members) return true
        return false
    }

    fun bandChat(message: String) {
        viewModelScope.launch(exceptionHandler) {
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
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
                        lastMessage = message,
                        picture = bandItem.photo,
                        name = bandItem.name
                    )
                )
            }
            addMessageUseCase.invoke(
                MessageItem(
                    id = getNewMessageId(),
                    chatId = chatId,
                    fromUser = getMyUserUseCase.invoke(),
                    timestamp = currentDate,
                    content = message
                )
            )
        }
    }

    fun changeBandProfile(
        oldProfile: BandItem,
        bandName: String,
        genre: String,
        profilePictureUrl: String
    ) {
        viewModelScope.launch(exceptionHandler) {
            val newBand = BandItem(
                id = oldProfile.id,
                name = bandName,
                members = oldProfile.members,
                genre = genre,
                photo = PhotoUrlItem(
                    id = oldProfile.id,
                    url = profilePictureUrl
                ),
            )
            editBandUseCase.invoke(newBand)
            changeBandPosts(bandName, profilePictureUrl)
        }

    }

    private fun changeBandPosts(userName: String, profilePictureUrl: String) {
        viewModelScope.launch(exceptionHandler) {
            for (i in postListFlow.value) {
                if (i.creatorId == bandItem.id) {
                    i.creatorName = userName
                    i.creatorPicture.url = profilePictureUrl
                    editPostUseCase.invoke(i)
                }
            }
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

}