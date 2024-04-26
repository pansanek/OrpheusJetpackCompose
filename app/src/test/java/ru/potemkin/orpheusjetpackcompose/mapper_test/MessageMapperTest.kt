import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals

import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import ru.potemkin.orpheusjetpackcompose.data.mappers.LocationMapper
import ru.potemkin.orpheusjetpackcompose.data.mappers.UsersMapper
import ru.potemkin.orpheusjetpackcompose.data.model.LocationDto
import ru.potemkin.orpheusjetpackcompose.data.model.PhotoUrlDto
import ru.potemkin.orpheusjetpackcompose.data.model.UserDto
import ru.potemkin.orpheusjetpackcompose.data.model.UserSettingsDto
import ru.potemkin.orpheusjetpackcompose.data.model.create_requests.CreateLocationRequest
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import ru.potemkin.orpheusjetpackcompose.data.mappers.MessageMapper
import ru.potemkin.orpheusjetpackcompose.data.model.MessageDto
import ru.potemkin.orpheusjetpackcompose.data.model.create_requests.CreateMessageRequest
import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import java.util.*

class MessageMapperTest {

    private lateinit var messageMapper: MessageMapper
    private val userMapper: UsersMapper = mock()
    private val testUserDto = UserDto(
        "11",
        "pansanek",
        "Sasha Potemkin",
        "12341234",
        "1@gmail.com",
        "Just a drummer, guitarist, bassist etc.",
        "Musician",
        PhotoUrlDto(
            "111",
            "https://sun1-88.userapi.com/impg/SsYpAAyxKG2SXIKXfY8iBvf2BTxZH9XYP2PFmA/lSVeMDXQuDM.jpg?size=1435x1435&quality=95&sign=c2dff2cc261588cb4a712c853c116199&type=album"
        ),
        PhotoUrlDto(
            "112",
            "https://i.pinimg.com/originals/06/67/9c/06679c2e2ae5aee8cf25eedc4bb41b98.jpg"
        ),
        UserSettingsDto(true, true)
    )

    private val testUser = UserItem(
        "11",
        "pansanek",
        "Sasha Potemkin",
        "12341234",
        "1@gmail.com",
        "Just a drummer, guitarist, bassist etc.",
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
    )
    @Before
    fun setUp() {
        messageMapper = MessageMapper()
        messageMapper.userMapper = userMapper
    }

    @Test
    fun `test mapMessageList`() {
        // Given
        val messageDtoList = listOf(
            MessageDto("1", "1", testUserDto, "Hello", "1234567890"),
            MessageDto("2", "1", testUserDto, "Hi", "")
        )

        val expectedMessageItemList = listOf(
            MessageItem("1", messageDtoList[0].chatId, testUser, "Hello", "1234567890"),
            MessageItem("2", messageDtoList[1].chatId, testUser, "Hi", "")
        )

        whenever(userMapper.mapUser(testUserDto)).thenReturn(testUser)

        // When
        val mappedMessageList = messageMapper.mapMessageList(messageDtoList)

        // Then
        assertEquals(expectedMessageItemList, mappedMessageList)
    }

    @Test
    fun `test mapMessageToRequest`() {
        // Given
        val messageItem = MessageItem("1", "1", testUser, "1234567890","Hello")

        val expectedCreateMessageRequest = CreateMessageRequest(
            chat_id = messageItem.chatId,
            from_user = testUserDto,
            content = "Hello",
            timestamp = "1234567890"
        )

        whenever(userMapper.mapUserDto(testUser)).thenReturn(testUserDto)

        // When
        val createMessageRequest = messageMapper.mapMessageToRequest(messageItem)

        // Then
        assertEquals(expectedCreateMessageRequest, createMessageRequest)
    }

    @Test
    fun `test mapMessage`() {
        // Given
        val messageDto = MessageDto("1", "1", testUserDto, "Hello", "1234567890")

        val expectedMessageItem = MessageItem(
            "1", messageDto.chatId, testUser, "Hello", "1234567890"
        )

        whenever(userMapper.mapUser(testUserDto)).thenReturn(testUser)

        // When
        val mappedMessageItem = messageMapper.mapMessage(messageDto)

        // Then
        assertEquals(expectedMessageItem, mappedMessageItem)
    }
}
