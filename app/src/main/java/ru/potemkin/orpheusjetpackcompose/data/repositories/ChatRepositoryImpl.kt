package ru.potemkin.orpheusjetpackcompose.data.repositories

import ru.potemkin.orpheusjetpackcompose.data.mappers.ChatMapper
import ru.potemkin.orpheusjetpackcompose.data.mappers.MessageMapper
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.NotificationItem
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
            "51bdc118-e76b-4372-8678-6822658cetett",
            mutableListOf(
                UserItem(
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
                ), UserItem(
                    "51bdc118-e76b-4372-8678-6822658cefed",
                    "noahbadomens",
                    "Noah Sebastian",
                    "12341234",
                    "email@gmail.com",
                    "Vocalist for Bad Omens",
                    UserType.MUSICIAN,
                    PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://i.pinimg.com/originals/7a/bd/00/7abd00f199dff4ec1364663ce0b45ea3.jpg"),
                    PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de4","https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"),
                    UserSettingsItem(true,true)
                )
            ),
            "Oh my GOD!",
        ))
    }

    fun addMockMessageData(){
        addMessageItem(
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
            )
        )
        addMessageItem(
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
    }


}