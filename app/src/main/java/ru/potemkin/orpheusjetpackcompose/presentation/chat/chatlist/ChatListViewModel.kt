package ru.potemkin.orpheusjetpackcompose.presentation.chat.chatlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.ChatRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import javax.inject.Inject

class ChatListViewModel @Inject constructor() : ViewModel() {

    private val initialState = ChatListScreenState.Initial

    private val _screenState = MutableLiveData<ChatListScreenState>(initialState)
    val screenState: LiveData<ChatListScreenState> = _screenState

    private val repository = ChatRepositoryImpl()

    init {
        _screenState.value = ChatListScreenState.Loading
        loadChats()
    }
    private fun loadChats(){
        viewModelScope.launch {
//            val chats = repository.loadChats()
            val chats = addChatMockData()
            _screenState.value = ChatListScreenState.Chats(chats = chats)
        }
    }


    fun addChatMockData():List<ChatItem>{
        val mockData = mutableListOf<ChatItem>(
            ChatItem(
                "51bdc118-e76b-4372-8678-6822658cetett",
                mutableListOf(UserItem(
                    "51bdc118-e76b-4372-8678-6822658cefed",
                    "pansanek",
                    "Sasha",
                    "12341234",
                    "email@gmail.com",
                    "Hehe",
                    UserType.MUSICIAN,
                    PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                    PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de4","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                    UserSettingsItem(true,true)
                ),UserItem(
                    "51bdc118-e76b-4372-8678-6822658cefeh",
                    "sanpanek",
                    "Sasha",
                    "12341234",
                    "email@gmail.com",
                    "Hehe",
                    UserType.MUSICIAN,
                    PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                    PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de4","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                    UserSettingsItem(true,true)
                )),
                "Oh my GOD!",
            )
        )
        return mockData
    }
}