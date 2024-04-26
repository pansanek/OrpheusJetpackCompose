package ru.potemkin.orpheusjetpackcompose.repository_test

import android.util.Log
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.potemkin.orpheusjetpackcompose.data.repositories.ChatRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType

class ChatRepositoryTest {

    private lateinit var chatRepository: ChatRepositoryImpl

    @Before
    fun setUp() {
        chatRepository = ChatRepositoryImpl()
    }

    @Test
    fun `test addChatItem`() = runBlocking {
        // Given
        val chatItem = ChatItem(
            "1",
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
        )

        // When
        chatRepository.addChatItem(chatItem)

        // Then
        val chats = chatRepository.getLocalChatList()
        assertEquals(3, chats.size)
        assertEquals(chatItem, chatRepository.getChatItem("1"))
    }

    @Test
    fun `test editChatItem`() = runBlocking {
        // Given
        val originalChat = ChatItem(
            "1",
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
        )
        val updatedChat = ChatItem(
            "1",
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
            "Пока <3",
            PhotoUrlItem(
                "197",
                "https://sun1-91.userapi.com/s/v1/ig2/VuGmflKD09SOOd9MeZIZzPqQmdYqbyJbu5VuHJz8ur39YTANcs4FudgMJrmrzKao6_fdj0zO3nUTymhBXrQwaW6P.jpg?size=400x400&quality=95&crop=270,705,873,873&ava=1"
            ),
            "Антон Францон"
        )
        chatRepository.addChatItem(originalChat)

        // When
        chatRepository.editChatItem(updatedChat)

        // Then
        val chats = chatRepository.getLocalChatList()
        assertEquals(3, chats.size)
        assertEquals(updatedChat, chatRepository.getChatItem("1"))
    }

    @Test(expected = RuntimeException::class)
    fun `test getChatItem when chat does not exist`() {
        // When
        chatRepository.getChatItem("1")
    }

    @Test
    fun `test getChatItem when chat exists`() = runBlocking {
        // Given
        val chatItem = ChatItem(
            "1",
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
        )
        chatRepository.addChatItem(chatItem)

        // When
        val retrievedChat = chatRepository.getChatItem("1")

        // Then
        assertEquals(chatItem, retrievedChat)
    }

    @Test
    fun `test deleteChatItem`() = runBlocking {
        // Given
        val chatItem = ChatItem(
            "1",
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
        )
        chatRepository.addChatItem(chatItem)

        // When
        chatRepository.deleteChatItem(chatItem)

        // Then
        val chats = chatRepository.getLocalChatList()
        assertEquals(2, chats.size)
    }

    @Test
    fun `test addMessageItem`() = runBlocking {
        // Given
        val messageItem = MessageItem(
            "1",
            "1",
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

        // When
        chatRepository.addMessageItem(messageItem)

        // Then
        val messages = chatRepository.getLocalMessageList()
        assertEquals(messageItem, chatRepository.getMessageItem("1"))
    }

    @Test
    fun `test deleteMessageItem`() = runBlocking {
        // Given
        val messageItem = MessageItem(
            "1",
            "1",
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
        chatRepository.addMessageItem(messageItem)

        // When
        chatRepository.deleteMessageItem(messageItem)

        // Then
        val messages = chatRepository.getLocalMessageList()
        assertEquals(0, messages.size)
    }

    @Test(expected = RuntimeException::class)
    fun `test getMessageItem when message does not exist`() {
        // When
        chatRepository.getMessageItem("1")
    }

    @Test
    fun `test getMessageItem when message exists`() = runBlocking {
        // Given
        val messageItem = MessageItem(
            "1",
            "1",
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
        chatRepository.addMessageItem(messageItem)

        // When
        val retrievedMessage = chatRepository.getMessageItem("1")

        // Then
        assertEquals(messageItem, retrievedMessage)
    }


}
