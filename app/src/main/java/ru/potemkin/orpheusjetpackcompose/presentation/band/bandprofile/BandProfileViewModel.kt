package ru.potemkin.orpheusjetpackcompose.presentation.band.bandprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetBandPostsUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.GetMyUserUseCase
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class BandProfileViewModel @Inject constructor(
    bandItem: BandItem,
    getBandPostsUseCase: GetBandPostsUseCase,
    private val editBandUseCase: EditBandUseCase,
    private val getMyUserUseCase: GetMyUserUseCase,
    private val editPostUseCase: EditPostUseCase,
    private val getChatListUseCase: GetChatListUseCase,
    private val addChatItemUseCase: AddChatItemUseCase,
    private val getMessageListUseCase: GetMessageListUseCase,
    private val addMessageUseCase: AddMessageUseCase
) : ViewModel() {

    private val initialState = BandProfileScreenState.Initial

    private val _screenState = MutableLiveData<BandProfileScreenState>(initialState)
    val screenState: LiveData<BandProfileScreenState> = _screenState

    val postList = getBandPostsUseCase.invoke(bandItem.id)
    val members = bandItem.members
    val bandItem = bandItem
    init {
        _screenState.value = BandProfileScreenState.Loading
        loadPosts(bandItem)
    }

    private fun loadPosts(band: BandItem) {
        viewModelScope.launch {
//            val feedPosts = repository.loadPosts(band.id)
            _screenState.value = BandProfileScreenState.Band(
                band = band,
                posts = postList
            )
        }
    }
    fun isMyUserInBand():Boolean{
        if(getMyUserUseCase.invoke() in members) return true
        return false
    }

    fun bandChat(message:String){
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
        val currentDate = sdf.format(Date())
        var chatExists = false
        var chatId = getNewChatId(getMyUserUseCase.invoke())
        for (i in getChatListUseCase.invoke(getMyUserUseCase.invoke().id)) {
            if (members == i.users) {
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
                    picture =bandItem.photo,
                    name = bandItem.name
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

    fun changeBandProfile(
        oldProfile: BandItem,
        bandName: String,
        genre: String,
        profilePictureUrl: String
    ) {
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

    private fun changeBandPosts(userName: String, profilePictureUrl: String) {
        for (i in postList) {
            i.creatorName = userName
            i.creatorPicture.url = profilePictureUrl
            editPostUseCase.invoke(i)
        }

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

}