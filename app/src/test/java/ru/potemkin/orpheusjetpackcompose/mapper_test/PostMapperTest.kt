import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import ru.potemkin.orpheusjetpackcompose.data.mappers.BandMapper
import ru.potemkin.orpheusjetpackcompose.data.mappers.LocationMapper
import ru.potemkin.orpheusjetpackcompose.data.mappers.PostMapper
import ru.potemkin.orpheusjetpackcompose.data.mappers.UsersMapper
import ru.potemkin.orpheusjetpackcompose.data.model.CommentDto
import ru.potemkin.orpheusjetpackcompose.data.model.PhotoUrlDto
import ru.potemkin.orpheusjetpackcompose.data.model.PostDto
import ru.potemkin.orpheusjetpackcompose.data.model.UserDto
import ru.potemkin.orpheusjetpackcompose.data.model.UserSettingsDto
import ru.potemkin.orpheusjetpackcompose.data.model.create_requests.CreatePostRequest
import ru.potemkin.orpheusjetpackcompose.data.repositories.UserRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorType
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticType
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType

class PostMapperTest {

    private lateinit var postMapper: PostMapper
    private val userMapper: UsersMapper = mock()
    private val bandMapper: BandMapper = mock()
    private val locationMapper: LocationMapper = mock()
    private val userRepositoryImpl: UserRepositoryImpl = mock()
    private val testUserDto = UserDto(
        "12",
        "noahbadomens",
        "Ной Себастьян",
        "12341234",
        "email@gmail.com",
        "Вокалист",
        "MUSICIAN",
        PhotoUrlDto(
            "192",
            "https://i.pinimg.com/originals/7a/bd/00/7abd00f199dff4ec1364663ce0b45ea3.jpg"
        ),
        PhotoUrlDto(
            "1102",
            "https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"
        ),
        UserSettingsDto(true, true)
    )

    private val testUser = UserItem(
        "12",
        "noahbadomens",
        "Ной Себастьян",
        "12341234",
        "email@gmail.com",
        "Вокалист",
        UserType.MUSICIAN,
        PhotoUrlItem(
            "192",
            "https://i.pinimg.com/originals/7a/bd/00/7abd00f199dff4ec1364663ce0b45ea3.jpg"
        ),
        PhotoUrlItem(
            "1102",
            "https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"
        ),
        UserSettingsItem(true, true)
    )

    private val testPost =PostItem(
        id = "31",
        creatorName = "pansanek",
        creatorId = "11",
        creatorPicture = PhotoUrlItem(
            "191",
            "https://sun1-88.userapi.com/impg/SsYpAAyxKG2SXIKXfY8iBvf2BTxZH9XYP2PFmA/lSVeMDXQuDM.jpg?size=1435x1435&quality=95&sign=c2dff2cc261588cb4a712c853c116199&type=album"
        ),
        creatorType = CreatorType.USER,
        text = "Первый пост!",
        date = "15/2/2024 10:01",
        comments = mutableListOf<CommentItem>(
            CommentItem(
                "21",
                "31",
                UserItem(
                    "12",
                    "noahbadomens",
                    "Ной Себастьян",
                    "12341234",
                    "email@gmail.com",
                    "Вокалист",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "192",
                        "https://i.pinimg.com/originals/7a/bd/00/7abd00f199dff4ec1364663ce0b45ea3.jpg"
                    ),
                    PhotoUrlItem(
                        "1102",
                        "https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"
                    ),
                    UserSettingsItem(true, true)
                ),
                "Круто",
                "15/2/2024 12:37"
            )
        ),
        likes= listOf<String>(),
        attachment = PhotoUrlItem(
            "391",
            "https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"
        ),
        isLiked = false,
        statistics = mutableListOf(
            StatisticItem(StatisticType.LIKES, 0), StatisticItem(StatisticType.COMMENTS, 1)
        ),
    )

    private val testPostDto =PostDto(
        postId = "31",
        creatorName = "pansanek",
        creatorId = "11",
        creatorPicture = PhotoUrlDto(
            "191",
            "https://sun1-88.userapi.com/impg/SsYpAAyxKG2SXIKXfY8iBvf2BTxZH9XYP2PFmA/lSVeMDXQuDM.jpg?size=1435x1435&quality=95&sign=c2dff2cc261588cb4a712c853c116199&type=album"
        ),
        creatorType = "USER",
        text = "Первый пост!",
        date = "15/2/2024 10:01",
        comments = mutableListOf<CommentDto>(
            CommentDto(
                "21",
                "31",
                UserDto(
                    "12",
                    "noahbadomens",
                    "Ной Себастьян",
                    "12341234",
                    "email@gmail.com",
                    "Вокалист",
                    "MUSICIAN",
                    PhotoUrlDto(
                        "192",
                        "https://i.pinimg.com/originals/7a/bd/00/7abd00f199dff4ec1364663ce0b45ea3.jpg"
                    ),
                    PhotoUrlDto(
                        "1102",
                        "https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"
                    ),
                    UserSettingsDto(true, true)
                ),
                "Круто",
                "15/2/2024 12:37"
            )
        ),
        likes= listOf<String>(),
        attachment = PhotoUrlDto(
            "391",
            "https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"
        )
    )
    @Before
    fun setUp() {
        postMapper = PostMapper()
        postMapper.userMapper = userMapper
        postMapper.bandMapper = bandMapper
        postMapper.locationMapper = locationMapper
        postMapper.userRepositoryImpl = userRepositoryImpl

        val myUser = testUser
        runBlocking {
            whenever(userRepositoryImpl.getMyUser()).thenReturn(myUser)
        }
    }

    @Test
    fun `test mapPostList`() = runBlocking {
        // Given
        val postDtoList = listOf(
           testPostDto,testPostDto
        )

        val expectedPostItemList = listOf(
            testPost,testPost
        )

        whenever(userMapper.mapUser(any())).thenReturn(testUser)

        // When
        val mappedPostList = postMapper.mapPostList(postDtoList)

        // Then
        assertEquals(expectedPostItemList, mappedPostList)
    }

    @Test
    fun `test mapPostToRequest`() {
        // Given
        val creatorId = "1"
        val creatorType = "User"
        val caption = "Some text"

        val expectedCreatePostRequest = CreatePostRequest(
            creatorId,caption, creatorType
        )

        // When
        val createPostRequest = postMapper.mapPostToRequest(creatorId, creatorType, caption)

        // Then
        assertEquals(expectedCreatePostRequest, createPostRequest)
    }

    // Add more tests for mapPost and other methods if needed
}
