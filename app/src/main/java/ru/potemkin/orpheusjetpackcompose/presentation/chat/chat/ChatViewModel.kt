package ru.potemkin.orpheusjetpackcompose.presentation.chat.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.ChatRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType

import javax.inject.Inject

class ChatViewModel @Inject constructor(chatItem: ChatItem) : ViewModel() {

    private val initialState = ChatScreenState.Initial

    private val _screenState = MutableLiveData<ChatScreenState>(initialState)
    val screenState: LiveData<ChatScreenState> = _screenState

    private val repository = ChatRepositoryImpl()

    init {
        _screenState.value = ChatScreenState.Loading
        loadMessages(chatItem.id)
    }

    private fun loadMessages(chatId: String) {
        viewModelScope.launch {
//            val messages = repository.getMessageList(chatId)
            val messages = addMessagesMockData()
            _screenState.value = ChatScreenState.Messages(chatId = chatId, messages = messages)
        }
    }

    fun addMessagesMockData(): List<MessageItem> {
        val mockData = mutableListOf<MessageItem>(
            MessageItem(
                "51bdc118-e76b-4372-8678-6822658gewgweg",
                "51bdc118-e76b-4372-8678-6822658cetett",
                UserItem(
                    "51bdc118-e76b-4372-8678-6822658cefed",
                    "pansanek",
                    "Sasha",
                    "12341234",
                    "email@gmail.com",
                    "Hehe",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "b59ae42e-8859-441a-9a3a-2fca1b784de3",
                        "https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"
                    ),
                    PhotoUrlItem(
                        "b59ae42e-8859-441a-9a3a-2fca1b784de4",
                        "https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"
                    ),
                    UserSettingsItem(true, true)
                ),
                "16-02-24",
                "Hey!"
            ),
            MessageItem(
                "51bdc118-e76b-4372-8678-6822658gewgweh",
                "51bdc118-e76b-4372-8678-6822658cetett",
                UserItem(
                    "51bdc118-e76b-4372-8678-6822658ceabc",
                    "sanpanek",
                    "Sasha",
                    "12341234",
                    "email2@gmail.com",
                    "Hehehe",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "b59ae42e-8859-441a-9a3a-2fca1b784de3",
                        "https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"
                    ),
                    PhotoUrlItem(
                        "b59ae42e-8859-441a-9a3a-2fca1b784de4",
                        "https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"
                    ),
                    UserSettingsItem(true, true)
                ),
                "16-02-24",
                "OH MY GOD"
            )
        )
        return mockData
    }
}