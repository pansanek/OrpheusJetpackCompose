import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.potemkin.orpheusjetpackcompose.data.mappers.UsersMapper
import ru.potemkin.orpheusjetpackcompose.data.model.PhotoUrlDto
import ru.potemkin.orpheusjetpackcompose.data.model.UserDto
import ru.potemkin.orpheusjetpackcompose.data.model.UserSettingsDto
import ru.potemkin.orpheusjetpackcompose.data.model.create_requests.CreateUserRequest
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType

class UsersMapperTest {

    private lateinit var usersMapper: UsersMapper

    @Before
    fun setUp() {
        usersMapper = UsersMapper()
    }

    @Test
    fun `test mapUsers`() {
        // Given
        val userDtoList = listOf(
            UserDto("1", "john", "John Doe", "password", "john@example.com", "About John", "MUSICIAN",
                PhotoUrlDto("1", "url1"), PhotoUrlDto("2", "url2"), UserSettingsDto(true, true)
            ),
            UserDto("2", "jane", "Jane Smith", "password", "jane@example.com", "About Jane", "ADMINISTRATOR",
                PhotoUrlDto("3", "url3"), PhotoUrlDto("4", "url4"), UserSettingsDto(false, true))
        )

        val expectedUserItemList = listOf(
            UserItem("1", "john", "John Doe", "password", "john@example.com", "About John",
                UserType.MUSICIAN, PhotoUrlItem("1", "url1"), PhotoUrlItem("2", "url2"),
                UserSettingsItem(true, true)
            ),
            UserItem("2", "jane", "Jane Smith", "password", "jane@example.com", "About Jane",
                UserType.ADMINISTRATOR, PhotoUrlItem("3", "url3"), PhotoUrlItem("4", "url4"),
                UserSettingsItem(false, true))
        )

        // When
        val mappedUserList = usersMapper.mapUsers(userDtoList)

        // Then
        assertEquals(expectedUserItemList, mappedUserList)
    }

    @Test
    fun `test mapUser`() {
        // Given
        val userDto = UserDto("1", "john", "John Doe", "password", "john@example.com", "About John", "MUSICIAN",
            PhotoUrlDto("1", "url1"), PhotoUrlDto("2", "url2"), UserSettingsDto(true, true))

        val expectedUserItem = UserItem("1", "john", "John Doe", "password", "john@example.com", "About John",
            UserType.MUSICIAN, PhotoUrlItem("1", "url1"), PhotoUrlItem("2", "url2"), UserSettingsItem(true, true))

        // When
        val mappedUserItem = usersMapper.mapUser(userDto)

        // Then
        assertEquals(expectedUserItem, mappedUserItem)
    }

    @Test
    fun `test mapUserToRequest`() {
        // Given
        val userItem = UserItem("1", "john", "John Doe", "password", "john@example.com", "About John",
            UserType.MUSICIAN, PhotoUrlItem("1", "url1"), PhotoUrlItem("2", "url2"), UserSettingsItem(true, true))

        val expectedCreateUserRequest = CreateUserRequest("john", "John Doe", "password", "john@example.com",
            "About John", "MUSICIAN", PhotoUrlDto("1", "url1"), PhotoUrlDto("2", "url2"), UserSettingsDto(true, true))

        // When
        val createUserRequest = usersMapper.mapUserToRequest(userItem)

        // Then
        assertEquals(expectedCreateUserRequest, createUserRequest)
    }

    @Test
    fun `test mapUserDto`() {
        // Given
        val userItem = UserItem("1", "john", "John Doe", "password", "john@example.com", "About John",
            UserType.MUSICIAN, PhotoUrlItem("1", "url1"), PhotoUrlItem("2", "url2"), UserSettingsItem(true, true))

        val expectedUserDto = UserDto("1", "john", "John Doe", "password", "john@example.com", "About John", "MUSICIAN",
            PhotoUrlDto("1", "url1"), PhotoUrlDto("2", "url2"), UserSettingsDto(true, true))

        // When
        val mappedUserDto = usersMapper.mapUserDto(userItem)

        // Then
        assertEquals(expectedUserDto, mappedUserDto)
    }
}
