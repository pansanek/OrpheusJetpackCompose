package ru.potemkin.orpheusjetpackcompose.repository_test

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.potemkin.orpheusjetpackcompose.data.repositories.PostRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorType
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticType
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType

class PostRepositoryTest {

    private lateinit var postRepository: PostRepositoryImpl

    @Before
    fun setUp() {
        postRepository = PostRepositoryImpl()
    }

    @Test
    fun `test addPostItem`() = runBlocking {
        // Given
        val postItem = PostItem(
                id = "1",
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
                        "1",
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

        // When
        postRepository.addPostItem(postItem)

        // Then
        val posts = postRepository.getLocalPostList()
        assertEquals(1, posts.size)
        assertEquals(postItem, postRepository.getPostItem("1"))
    }

    @Test
    fun `test changeLikeStatus when post is liked`() = runBlocking {
        // Given
        val postItem = PostItem(
            id = "1",
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
                    "1",
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
            likes= listOf<String>("user1"),
            attachment = PhotoUrlItem(
                "391",
                "https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"
            ),
            isLiked = true,
            statistics = mutableListOf(
                StatisticItem(StatisticType.LIKES, 0), StatisticItem(StatisticType.COMMENTS, 1)
            ),
        )
        postRepository.addPostItem(postItem)

        // When
        postRepository.changeLikeStatus("1", "user1")

        // Then
        val updatedPost = postRepository.getPostItem("1")
        assertEquals(false, updatedPost.isLiked)
    }

    @Test
    fun `test deletePostItem`() = runBlocking {
        // Given
        val postItem = PostItem(
                id = "1",
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
                        "1",
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
        postRepository.addPostItem(postItem)

        // When
        postRepository.deletePostItem(postItem)

        // Then
        val posts = postRepository.getLocalPostList()
        assertEquals(0, posts.size)
    }

    @Test(expected = RuntimeException::class)
    fun `test getPostItem when post does not exist`(){
        // When
        runBlocking { postRepository.getPostItem("1") }
    }

    @Test
    fun `test getPostItem when post exists`() = runBlocking {
        // Given
        val postItem = PostItem(
                id = "1",
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
                        "1",
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
        postRepository.addPostItem(postItem)

        // When
        val retrievedPost = postRepository.getPostItem("1")

        // Then
        assertEquals(postItem, retrievedPost)
    }
}
