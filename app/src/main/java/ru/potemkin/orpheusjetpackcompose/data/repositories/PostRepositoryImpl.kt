package ru.potemkin.orpheusjetpackcompose.data.repositories

import ru.potemkin.orpheusjetpackcompose.data.mappers.PostMapper
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorType
import ru.potemkin.orpheusjetpackcompose.domain.entities.NotificationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticType
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.domain.repositories.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(

) : PostRepository {

    private val apiService = ApiFactory.appPostApiService
    private val mapper = PostMapper()

    private val _postItems = mutableListOf<PostItem>()
    private val postItems: List<PostItem>
        get() = _postItems.toList()

    private var nextFrom: String? = null

    suspend fun loadRecommendations(): List<PostItem> {
        val startFrom = nextFrom

        if (startFrom == null && postItems.isNotEmpty()) return postItems

        val response = if (startFrom == null) {
            apiService.getAllPosts()
        } else {
            apiService.getAllPosts()
        }
        val posts = mapper.mapPostList(response)
        _postItems.addAll(posts)
        return postItems
    }

    init {
        addMockData()
    }

    override fun addPostItem(postItem: PostItem) {
        _postItems.add(postItem)
    }

    override fun deletePostItem(postItem: PostItem) {
        _postItems.remove(postItem)
    }

    override fun getComments(postItem: PostItem): List<CommentItem> {
        return postItem.comments
    }

    override fun addCommentItem(commentItem: CommentItem) {

    }

    override fun editPostItem(postItem: PostItem) {
        val oldElement = getPostItem(postItem.id)
        _postItems.remove(oldElement)
        addPostItem(postItem)
    }

    override fun getPostItem(postId: String): PostItem {
        return postItems.find {
            it.id == postId
        } ?: throw java.lang.RuntimeException("Element with id $postId not found")
    }


    override fun getPostsList(): List<PostItem> = _postItems

    override suspend fun loadNextData() {
        TODO("Not yet implemented")
    }

    override fun getUserPosts(userId: String): List<PostItem> {
        val userPosts = mutableListOf<PostItem>()
        for (post in _postItems) {
            if (post.creatorId == userId) userPosts.add(post)
        }
        return userPosts
    }

    override fun getBandPosts(bandId: String): List<PostItem> {
        val bandPosts = mutableListOf<PostItem>()
        for (post in _postItems) {
            if (post.creatorId == bandId) bandPosts.add(post)
        }
        return bandPosts
    }

    override fun getLocationPosts(locationId: String): List<PostItem> {
        val locationPosts = mutableListOf<PostItem>()
        for (post in _postItems) {
            if (post.creatorId == locationId) locationPosts.add(post)
        }
        return locationPosts
    }

    fun addMockData(){
        addPostItem(
            PostItem(
                id = "a9d28f2a-5eae-48bf-85f7-7c8dde3ec22c",
                creatorName = "pansanek",
                creatorId = "a9d28f2a-5eae-48bf-85f7-7c8dde3ec23c",
                creatorPicture = PhotoUrlItem(
                    "b59ae42e-8859-441a-9a3a-2fca1b784de3",
                    "https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"
                ),
                creatorType = CreatorType.USER,
                text = "Test post",
                date = "01-07-21",
                comments = mutableListOf<CommentItem>(
                    CommentItem(
                        "5600434b-7627-45fc-af8b-9f92e838c2e7",
                        "a9d28f2a-5eae-48bf-85f7-7c8dde3ec22c",
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
                        "Nice",
                        "01-07-21"
                    )
                ),
                attachment = PhotoUrlItem(
                    "b59ae42e-8859-441a-9a3a-2fca1b784de5",
                    "https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"
                ),
                isLiked = true,
                statistics = mutableListOf(
                    StatisticItem(StatisticType.LIKES, 21), StatisticItem(StatisticType.COMMENTS, 1)
                ),
            )
        )
        addPostItem(
            PostItem(
                id = "a9d28f2a-5eae-48bf-85f7-7c8dde3ec23c",
                creatorName = "Dealer",
                creatorId = "a9d28f2a-5eae-48bf-85f7-7c8dde3ec23c",
                creatorPicture = PhotoUrlItem(
                    "b59ae42e-8859-441a-9a3a-2fca1b784de3",
                    "https://www.noecho.net/uploads/wysiwyg/56398448_2377037402530657_3620978213045403648_o.jpg"
                ),
                creatorType = CreatorType.BAND,
                text = "RED TEETH OUT NOW!!!",
                date = "01-02-24",
                comments = mutableListOf<CommentItem>(
                    CommentItem(
                        "5600434b-7627-45fc-af8b-9f92e838c2e7",
                        "a9d28f2a-5eae-48bf-85f7-7c8dde3ec22c",
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
                        "Nice",
                        "01-02-24"
                    ),
                    CommentItem(
                        "5600434b-7627-45fc-af8b-9f92e838c2e8",
                        "a9d28f2a-5eae-48bf-85f7-7c8dde3ec22c",
                        UserItem(
                            "51bdc118-e76b-4372-8678-6822658ceffe",
                            "pansanekk",
                            "Sasha",
                            "12341234",
                            "email@gmail.com",
                            "Hehe",
                            UserType.MUSICIAN,
                            PhotoUrlItem(
                                "b59ae42e-8859-441a-9a3a-2fca1b784de3",
                                "https://images6.fanpop.com/image/photos/38800000/-Matt-Nicholls-Upset-Magazine-Portrait-bring-me-the-horizon-38883120-1500-2250.jpg"
                            ),
                            PhotoUrlItem(
                                "b59ae42e-8859-441a-9a3a-2fca1b784de4",
                                "https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"
                            ),
                            UserSettingsItem(true, true)
                        ),
                        "Nice",
                        "01-02-24"
                    )
                ),
                attachment = PhotoUrlItem(
                    "b59ae42e-8859-441a-9a3a-2fca1b784de5",
                    "https://thenewfury.com/wp-content/uploads/2020/06/71892285_2515240622043667_2289500113191567360_o-e1592843931224.jpg"
                ),
                isLiked = true,
                statistics = mutableListOf(
                    StatisticItem(StatisticType.LIKES, 21), StatisticItem(StatisticType.COMMENTS, 2)
                ),
            )
        )
    }

}