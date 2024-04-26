import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
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

class LocationMapperTest {

    private lateinit var locationMapper: LocationMapper
    private val usersMapper: UsersMapper = mock()
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
        locationMapper = LocationMapper()
        locationMapper.adminMapper = usersMapper
    }

    @Test
    fun `test mapLocations`() = runBlocking {
        // Given
        val locationDtoList = listOf(
            LocationDto("1", testUserDto, "Location 1", "Address 1", "About 1", PhotoUrlDto("1", "url1")),
            LocationDto("2", testUserDto, "Location 2", "Address 2", "About 2", PhotoUrlDto("2", "url2"))
        )

        val expectedLocationItemList = listOf(
            LocationItem("1", testUser, "Location 1", "Address 1", "About 1", PhotoUrlItem("1", "url1"), 0.0, 0.0),
            LocationItem("2", testUser, "Location 2", "Address 2", "About 2", PhotoUrlItem("2", "url2"), 0.0, 0.0)
        )

        whenever(usersMapper.mapUser(any())).thenReturn(testUser)

        // When
        val mappedLocationList = locationMapper.mapLocations(locationDtoList)

        // Then
        assertEquals(expectedLocationItemList, mappedLocationList)
    }

    @Test
    fun `test mapLocationToRequest`() {
        // Given
        val locationItem = LocationItem("1", testUser, "Location 1", "Address 1", "About 1", PhotoUrlItem("1", "url1"), 0.0, 0.0)

        val expectedCreateLocationRequest = CreateLocationRequest(
            testUserDto, "Location 1", "Address 1", "About 1", PhotoUrlDto("1", "url1")
        )

        whenever(usersMapper.mapUserDto(any())).thenReturn(testUserDto)

        // When
        val createLocationRequest = locationMapper.mapLocationToRequest(locationItem)

        // Then
        assertEquals(expectedCreateLocationRequest, createLocationRequest)
    }

    @Test
    fun `test mapLocation`() {
        // Given
        val locationDto = LocationDto("1", testUserDto, "Location 1", "Address 1", "About 1", PhotoUrlDto("1", "url1"))

        val expectedLocationItem = LocationItem("1", testUser, "Location 1", "Address 1", "About 1", PhotoUrlItem("1", "url1"), 0.0, 0.0)

        whenever(usersMapper.mapUser(any())).thenReturn(testUser)

        // When
        val mappedLocationItem = locationMapper.mapLocation(locationDto)

        // Then
        assertEquals(expectedLocationItem, mappedLocationItem)
    }
}
