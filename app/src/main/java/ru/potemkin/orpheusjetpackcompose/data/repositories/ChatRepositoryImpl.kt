package ru.potemkin.orpheusjetpackcompose.data.repositories

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import ru.potemkin.orpheusjetpackcompose.data.mappers.ChatMapper
import ru.potemkin.orpheusjetpackcompose.data.mappers.MessageMapper
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.domain.repositories.ChatRepository
import ru.potemkin.orpheusjetpackcompose.extentions.mergeWith
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(

): ChatRepository {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val apiService = ApiFactory.appChatApiService
    private val messageApiService = ApiFactory.appMessageApiService
    private val mapper = ChatMapper()
    private val messageMapper = MessageMapper()

    private val _chatItems = mutableListOf<ChatItem>()
    private val chatItems: List<ChatItem>
        get() = _chatItems.toList()

    private val _messageItems = mutableListOf<MessageItem>()
    private val messageItems: List<MessageItem>
        get() = _messageItems.toList()

    init {
    }
    private var nextFrom: String? = null

    private val refreshedChatListFlow = MutableSharedFlow<List<ChatItem>>()
    private val loadedChatListFlow = flow {
        // Проверяем, есть ли уже какие-то посты, и если есть, то их сначала отправляем
        if (chatItems.isNotEmpty()) {
            emit(chatItems)
        } else {
            addMockChatData()
            emit(chatItems)
        }
    }.retry {
        delay(RETRY_TIMEOUT_MILLIS)
        true
    }
    private val chats: StateFlow<List<ChatItem>> = loadedChatListFlow
        .mergeWith(refreshedChatListFlow)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Eagerly,
            initialValue = chatItems
        )

    private val refreshedMessageListFlow = MutableSharedFlow<List<MessageItem>>()
    private val loadedMessageListFlow = flow {
        // Проверяем, есть ли уже какие-то посты, и если есть, то их сначала отправляем
        if (messageItems.isNotEmpty()) {
            emit(messageItems)
        } else {
            addMockMessageData()
            emit(messageItems)
        }
    }.retry {
        delay(RETRY_TIMEOUT_MILLIS)
        true
    }
    private val messages: StateFlow<List<MessageItem>> = loadedMessageListFlow
        .mergeWith(refreshedMessageListFlow)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Eagerly,
            initialValue = messageItems
        )

    override suspend fun addChatItem(chatItem: ChatItem) {
        _chatItems.add(chatItem)
        refreshedChatListFlow.emit(chatItems)
    }

    override suspend fun editChatItem(chatItem: ChatItem) {
        val oldElement = getChatItem(chatItem.id)
        _chatItems.remove(oldElement)
        addChatItem(chatItem)
    }

    override fun getChatItem(chatId: String): ChatItem {
        return chatItems.find {
            it.id == chatId
        } ?: throw java.lang.RuntimeException("Element with id $chatId not found")
    }

    override fun getChatList(): StateFlow<List<ChatItem>> =chats


    override suspend fun deleteChatItem(chatItem: ChatItem) {
        _chatItems.remove(chatItem)
        refreshedChatListFlow.emit(chatItems)
    }

    override suspend fun addMessageItem(messageItem: MessageItem) {
        _messageItems.add(messageItem)
        refreshedMessageListFlow.emit(messageItems)
    }

    override suspend fun deleteMessageItem(messageItem: MessageItem) {
        _messageItems.remove(messageItem)
        refreshedMessageListFlow.emit(messageItems)
    }
    override fun getMessageItem(messageId: String): MessageItem {
        return _messageItems.find {
            it.id == messageId
        } ?: throw java.lang.RuntimeException("Element with id $messageId not found")
    }
    override suspend fun editMessageItem(messageItem: MessageItem) {
        val oldElement = getMessageItem(messageItem.id)
        _messageItems.remove(oldElement)
        addMessageItem(messageItem)
    }

    override fun getMessageList(): StateFlow<List<MessageItem>> = messages

    fun getLocalChatList(): List<ChatItem>  = _chatItems
    fun getLocalMessageList(): List<MessageItem>  = _messageItems
    suspend fun addMockChatData(){
        addChatItem(ChatItem(
            "51",
            mutableListOf(
                UserItem(
                    "11",
                    "pansanek",
                    "Саша Потемкин",
                    "12341234",
                    "1@gmail.com",
                    "Просто барабанщик, гитарист, басист и так далее.",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "111",
                        "https://sun1-88.userapi.com/impg/SsYpAAyxKG2SXIKXfY8iBvf2BTxZH9XYP2PFmA/lSVeMDXQuDM.jpg?size=1435x1435&quality=95&sign=c2dff2cc261588cb4a712c853c116199&type=album"
                    ),
                    PhotoUrlItem(
                        "112",
                        "https://i.pinimg.com/originals/06/67/9c/06679c2e2ae5aee8cf25eedc4bb41b98.jpg"
                    ),
                    UserSettingsItem(true, true)
                ), UserItem(
                    "17",
                    "antonfranzon",
                    "Антон Францон",
                    "12341234",
                    "email@gmail.com",
                    "Барабанщик группы Normandie",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "197",
                        "https://sun1-91.userapi.com/s/v1/ig2/VuGmflKD09SOOd9MeZIZzPqQmdYqbyJbu5VuHJz8ur39YTANcs4FudgMJrmrzKao6_fdj0zO3nUTymhBXrQwaW6P.jpg?size=400x400&quality=95&crop=270,705,873,873&ava=1"
                    ),
                    PhotoUrlItem(
                        "1107",
                        "https://sun9-80.userapi.com/impf/c855524/v855524135/19eaeb/VfxIoZvQHGA.jpg?size=604x403&quality=96&sign=d9899d7b718557643742be9c124e1e5a&type=album"
                    ),
                    UserSettingsItem(true, true)
                )
            ),
            "Спасибо <3",
            PhotoUrlItem(
                "197",
                "https://sun1-91.userapi.com/s/v1/ig2/VuGmflKD09SOOd9MeZIZzPqQmdYqbyJbu5VuHJz8ur39YTANcs4FudgMJrmrzKao6_fdj0zO3nUTymhBXrQwaW6P.jpg?size=400x400&quality=95&crop=270,705,873,873&ava=1"
            ),
            "Антон Францон"
        ))
        addChatItem(ChatItem(
            "52",
            mutableListOf(
                UserItem(
                    "11",
                    "pansanek",
                    "Саша Потемкин",
                    "12341234",
                    "1@gmail.com",
                    "Просто барабанщик, гитарист, басист и так далее.",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "111",
                        "https://sun1-88.userapi.com/impg/SsYpAAyxKG2SXIKXfY8iBvf2BTxZH9XYP2PFmA/lSVeMDXQuDM.jpg?size=1435x1435&quality=95&sign=c2dff2cc261588cb4a712c853c116199&type=album"
                    ),
                    PhotoUrlItem(
                        "112",
                        "https://i.pinimg.com/originals/06/67/9c/06679c2e2ae5aee8cf25eedc4bb41b98.jpg"
                    ),
                    UserSettingsItem(true, true)
                ), UserItem(
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
                )
            ),
            "Ты хочешь создать со мной группу?",
            PhotoUrlItem(
                "197",
                "https://sun9-25.userapi.com/impf/c840320/v840320259/36208/h5GVeRP9URM.jpg?size=640x640&quality=96&sign=f5307f49e081c58b7cbb3bbb4680efb6&c_uniq_tag=FbHPADgjU38jiYwFHjugwpbBeRJbDbBXfs4fCfTv3rk&type=album"
            ),
            "Лэндон Тьюерс"
        ))
    }

    suspend fun addMockMessageData(){
        addMessageItem(
            MessageItem(
                "651",
                "51",
                UserItem(
                    "11",
                    "pansanek",
                    "Саша Потемкин",
                    "12341234",
                    "1@gmail.com",
                    "Просто барабанщик, гитарист, басист и так далее.",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "111",
                        "https://sun1-88.userapi.com/impg/SsYpAAyxKG2SXIKXfY8iBvf2BTxZH9XYP2PFmA/lSVeMDXQuDM.jpg?size=1435x1435&quality=95&sign=c2dff2cc261588cb4a712c853c116199&type=album"
                    ),
                    PhotoUrlItem(
                        "112",
                        "https://i.pinimg.com/originals/06/67/9c/06679c2e2ae5aee8cf25eedc4bb41b98.jpg"
                    ),
                    UserSettingsItem(true, true)
                ),
                "16-02-24",
                "Ваш последний альбом просто супер!"
            )
        )
        addMessageItem(
            MessageItem(
                "652",
                "51",
                UserItem(
                    "17",
                    "antonfranzon",
                    "Антон Францон",
                    "12341234",
                    "email@gmail.com",
                    "Барабанщик группы Normandie",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "197",
                        "https://sun1-91.userapi.com/s/v1/ig2/VuGmflKD09SOOd9MeZIZzPqQmdYqbyJbu5VuHJz8ur39YTANcs4FudgMJrmrzKao6_fdj0zO3nUTymhBXrQwaW6P.jpg?size=400x400&quality=95&crop=270,705,873,873&ava=1"
                    ),
                    PhotoUrlItem(
                        "1107",
                        "https://sun9-80.userapi.com/impf/c855524/v855524135/19eaeb/VfxIoZvQHGA.jpg?size=604x403&quality=96&sign=d9899d7b718557643742be9c124e1e5a&type=album"
                    ),
                    UserSettingsItem(true, true)
                ),
                "16-02-24",
                "Спасибо <3"
            )
        )
        addMessageItem(
            MessageItem(
                "653",
                "52",
                UserItem(
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
                "16-02-24",
                "Ты хочешь создать со мной группу?"
            )
        )
    }



    companion object {

        private const val RETRY_TIMEOUT_MILLIS = 5L
    }


}