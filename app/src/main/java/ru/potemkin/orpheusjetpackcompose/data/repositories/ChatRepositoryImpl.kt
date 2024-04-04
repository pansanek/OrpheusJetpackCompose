package ru.potemkin.orpheusjetpackcompose.data.repositories

import ru.potemkin.orpheusjetpackcompose.data.mappers.ChatMapper
import ru.potemkin.orpheusjetpackcompose.data.mappers.MessageMapper
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.domain.repositories.ChatRepository
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(

): ChatRepository {


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

    private var nextFrom: String? = null

    init {
        addMockChatData()
        addMockMessageData()
    }

    override fun addChatItem(chatItem: ChatItem) {
        _chatItems.add(chatItem)
    }

    override fun editChatItem(chatItem: ChatItem) {
        val oldElement = getChatItem(chatItem.id)
        _chatItems.remove(oldElement)
        addChatItem(chatItem)
    }

    override fun getChatItem(chatId: String): ChatItem {
        return chatItems.find {
            it.id == chatId
        } ?: throw java.lang.RuntimeException("Element with id $chatId not found")
    }

    override fun getChatList(userId: String): List<ChatItem> {
        val chats = mutableListOf<ChatItem>()
        for (chat in _chatItems){
            for (user in chat.users) {
                if (user.id == userId) chats.add(chat)
            }
        }
        return chats
    }


    override fun deleteChatItem(chatItem: ChatItem) {
        _chatItems.remove(chatItem)
    }

    override fun addMessageItem(messageItem: MessageItem) {
        _messageItems.add(messageItem)
    }

    override fun deleteMessageItem(messageItem: MessageItem) {
        _messageItems.remove(messageItem)
    }
    override fun getMessageItem(messageId: String): MessageItem {
        return _messageItems.find {
            it.id == messageId
        } ?: throw java.lang.RuntimeException("Element with id $messageId not found")
    }
    override fun editMessageItem(messageItem: MessageItem) {
        val oldElement = getMessageItem(messageItem.id)
        _messageItems.remove(oldElement)
        addMessageItem(messageItem)
    }

    override fun getMessageList(chatId: String): List<MessageItem> {
        val chatMessage = mutableListOf<MessageItem>()
        for (message in _messageItems){
            if(message.chatId == chatId) chatMessage.add(message)
        }
        return chatMessage
    }

    fun addMockChatData(){
        addChatItem(ChatItem(
            "51",
            mutableListOf(
                UserItem(
                    "11",
                    "pansanek",
                    "Sasha Potemkin",
                    "12341234",
                    "1@gmail.com",
                    "Just a drummer, guitarist, bassist etc.",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "191",
                        "https://sun1-88.userapi.com/impg/SsYpAAyxKG2SXIKXfY8iBvf2BTxZH9XYP2PFmA/lSVeMDXQuDM.jpg?size=1435x1435&quality=95&sign=c2dff2cc261588cb4a712c853c116199&type=album"
                    ),
                    PhotoUrlItem(
                        "1101",
                        "https://i.pinimg.com/originals/06/67/9c/06679c2e2ae5aee8cf25eedc4bb41b98.jpg"
                    ),
                    UserSettingsItem(true, true)
                ), UserItem(
                    "17",
                    "antonfranzon",
                    "Anton Franzon",
                    "12341234",
                    "email@gmail.com",
                    "Drummer for Normandie",
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
            "OH MY GOD",
        ))
    }

    fun addMockMessageData(){
        addMessageItem(
            MessageItem(
                "61",
                "51",
                UserItem(
                    "11",
                    "pansanek",
                    "Sasha Potemkin",
                    "12341234",
                    "1@gmail.com",
                    "Just a drummer, guitarist, bassist etc.",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "191",
                        "https://sun1-88.userapi.com/impg/SsYpAAyxKG2SXIKXfY8iBvf2BTxZH9XYP2PFmA/lSVeMDXQuDM.jpg?size=1435x1435&quality=95&sign=c2dff2cc261588cb4a712c853c116199&type=album"
                    ),
                    PhotoUrlItem(
                        "1101",
                        "https://i.pinimg.com/originals/06/67/9c/06679c2e2ae5aee8cf25eedc4bb41b98.jpg"
                    ),
                    UserSettingsItem(true, true)
                ),
                "16-02-24",
                "Hey!"
            )
        )
        addMessageItem(
            MessageItem(
                "62",
                "51",
                UserItem(
                    "17",
                    "antonfranzon",
                    "Anton Franzon",
                    "12341234",
                    "email@gmail.com",
                    "Drummer for Normandie",
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
                "OH MY GOD"
            )
        )
    }


}