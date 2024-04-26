import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import ru.potemkin.orpheusjetpackcompose.data.mappers.ChatMapper
import ru.potemkin.orpheusjetpackcompose.data.mappers.UsersMapper
import ru.potemkin.orpheusjetpackcompose.data.model.ChatDto
import ru.potemkin.orpheusjetpackcompose.data.model.PhotoUrlDto
import ru.potemkin.orpheusjetpackcompose.data.model.create_requests.CreateChatRequest
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem

class ChatMapperTest {

    private lateinit var chatMapper: ChatMapper
    private val userMapper: UsersMapper = mock()

    @Before
    fun setUp() {
        chatMapper = ChatMapper()
        chatMapper.userMapper = userMapper
    }

    @Test
    fun `test mapChatList`() {
        // Given
        val chatDtoList = listOf(
            ChatDto("1", emptyList(), "Last message 1", PhotoUrlDto("1", "url1"), "Chat 1"),
            ChatDto("2", emptyList(), "Last message 2", PhotoUrlDto("2", "url2"), "Chat 2")
        )

        val expectedChatItemList = listOf(
            ChatItem("1", emptyList(), "Last message 1", PhotoUrlItem("1", "url1"), "Chat 1"),
            ChatItem("2", emptyList(), "Last message 2", PhotoUrlItem("2", "url2"), "Chat 2")
        )

        whenever(userMapper.mapUsers(emptyList())).thenReturn(emptyList())

        // When
        val mappedChatList = chatMapper.mapChatList(chatDtoList)

        // Then
        assertEquals(expectedChatItemList, mappedChatList)
    }

    @Test
    fun `test mapChatToRequest`() {
        // Given
        val chatItem = ChatItem(
            "1", emptyList(), "Last message", PhotoUrlItem("1", "url"), "Chat"
        )

        val expectedCreateChatRequest = CreateChatRequest(
            emptyList(), "Last message", PhotoUrlDto("1", "url"), "Chat"
        )

        whenever(userMapper.mapUsers(emptyList())).thenReturn(emptyList())

        // When
        val createChatRequest = chatMapper.mapChatToRequest(chatItem)

        // Then
        assertEquals(expectedCreateChatRequest, createChatRequest)
    }

    @Test
    fun `test mapChat`() {
        // Given
        val chatDto = ChatDto("1", emptyList(), "Last message", PhotoUrlDto("1", "url"), "Chat")

        val expectedChatItem = ChatItem("1", emptyList(), "Last message", PhotoUrlItem("1", "url"), "Chat")

        whenever(userMapper.mapUsers(emptyList())).thenReturn(emptyList())

        // When
        val mappedChatItem = chatMapper.mapChat(chatDto)

        // Then
        assertEquals(expectedChatItem, mappedChatItem)
    }
}
