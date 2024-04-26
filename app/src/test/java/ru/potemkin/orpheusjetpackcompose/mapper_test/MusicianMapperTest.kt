import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import ru.potemkin.orpheusjetpackcompose.data.mappers.MusicianMapper
import ru.potemkin.orpheusjetpackcompose.data.mappers.UsersMapper
import ru.potemkin.orpheusjetpackcompose.data.model.MusicianDto
import ru.potemkin.orpheusjetpackcompose.data.model.PhotoUrlDto
import ru.potemkin.orpheusjetpackcompose.data.model.UserDto
import ru.potemkin.orpheusjetpackcompose.data.model.UserSettingsDto
import ru.potemkin.orpheusjetpackcompose.data.model.create_requests.CreateMusicianRequest
import ru.potemkin.orpheusjetpackcompose.domain.entities.MusicianItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import java.util.*

class MusicianMapperTest {

    private lateinit var musicianMapper: MusicianMapper
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
        musicianMapper = MusicianMapper()
        musicianMapper.userMapper = userMapper
    }

    @Test
    fun `test mapMusicians`() {
        // Given
        val musicianDtoList = listOf(
            MusicianDto("1", testUserDto, "Rock", "Guitar"),
            MusicianDto("2", testUserDto, "Pop", "Piano")
        )

        val expectedMusicianItemList = listOf(
            MusicianItem("1", testUser, "Rock", "Guitar"),
            MusicianItem("2", testUser, "Pop", "Piano")
        )

        whenever(userMapper.mapUser(testUserDto)).thenReturn(testUser)
        whenever(userMapper.mapUser(testUserDto)).thenReturn(testUser)

        // When
        val mappedMusicianList = musicianMapper.mapMusicians(musicianDtoList)

        // Then
        assertEquals(expectedMusicianItemList, mappedMusicianList)
    }


    @Test
    fun `test mapMusician`() {
        // Given
        val musicianDto = MusicianDto("1", testUserDto, "Rock", "Guitar")

        val expectedMusicianItem = MusicianItem("1", testUser, "Rock", "Guitar")

        whenever(userMapper.mapUser(testUserDto)).thenReturn(testUser)

        // When
        val mappedMusicianItem = musicianMapper.mapMusician(musicianDto)

        // Then
        assertEquals(expectedMusicianItem, mappedMusicianItem)
    }
}
