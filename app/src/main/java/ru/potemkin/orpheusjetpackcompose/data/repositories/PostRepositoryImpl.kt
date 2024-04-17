package ru.potemkin.orpheusjetpackcompose.data.repositories

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.potemkin.orpheusjetpackcompose.data.mappers.PostMapper
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorType
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticType
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.domain.repositories.PostRepository
import ru.potemkin.orpheusjetpackcompose.extentions.mergeWith
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(

) : PostRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val apiService = ApiFactory.appPostApiService
    private val mapper = PostMapper()

    private val _postItems = mutableListOf<PostItem>()
    private val postItems: List<PostItem>
        get() = _postItems.toList()

    private var nextFrom: String? = null

    private val refreshedListFlow = MutableSharedFlow<List<PostItem>>()
    private val loadedListFlow = flow {
        // Проверяем, есть ли уже какие-то посты, и если есть, то их сначала отправляем
        if (postItems.isNotEmpty()) {
            emit(postItems)
        } else {
            // Если постов нет, добавляем моковые данные
            addMockData()
            emit(postItems)
        }
    }.retry {
        delay(RETRY_TIMEOUT_MILLIS)
        true
    }
    private val recommendations: StateFlow<List<PostItem>> = loadedListFlow
        .mergeWith(refreshedListFlow)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Eagerly,
            initialValue = postItems
        )

    override suspend fun addPostItem(postItem: PostItem) {
        _postItems.add(postItem)
        refreshedListFlow.emit(postItems)
    }

    override suspend fun changeLikeStatus(postItemId: String) {
        val oldElement = getPostItem(postItemId)
        if(oldElement.isLiked == false) {
            oldElement.isLiked = true
            oldElement.statistics.get(0).count += 1
            _postItems.remove(oldElement)
            addPostItem(oldElement)
        }
        else{
            oldElement.isLiked = false
            oldElement.statistics.get(0).count -= 1
            _postItems.remove(oldElement)
            addPostItem(oldElement)
        }

    }

    override suspend fun deletePostItem(postItem: PostItem) {
        _postItems.remove(postItem)
        refreshedListFlow.emit(postItems)
    }

    override fun getComments(postItem: PostItem): StateFlow<List<CommentItem>> {
        return MutableStateFlow(postItem.comments)
    }

    override suspend fun addCommentItem(commentItem: CommentItem) {
        val postItem = getPostItem(commentItem.post_id)
        _postItems.remove(postItem)
        var comments = mutableListOf(commentItem)
        for (i in postItem.comments) comments.add(i)
        postItem.comments = comments
        postItem.statistics.get(1).count +=1
        addPostItem(postItem)
    }

    override suspend fun editPostItem(postItem: PostItem) {
        val oldElement = getPostItem(postItem.id)
        _postItems.remove(oldElement)
        addPostItem(postItem)
    }

    override suspend fun getPostItem(postId: String): PostItem {
        return postItems.find {
            it.id == postId
        } ?: throw java.lang.RuntimeException("Element with id $postId not found")
    }


    override fun getPostsList(): StateFlow<List<PostItem>>  = recommendations

    override suspend fun loadNextData() {
        TODO("Not yet implemented")
    }



    suspend fun addMockData() {
        addPostItem(
            PostItem(
                id = "31",
                creatorName = "pansanek",
                creatorId = "11",
                creatorPicture = PhotoUrlItem(
                    "191",
                    "https://sun1-88.userapi.com/impg/SsYpAAyxKG2SXIKXfY8iBvf2BTxZH9XYP2PFmA/lSVeMDXQuDM.jpg?size=1435x1435&quality=95&sign=c2dff2cc261588cb4a712c853c116199&type=album"
                ),
                creatorType = CreatorType.USER,
                text = "First Post!",
                date = "15/2/2024 10:01",
                comments = mutableListOf<CommentItem>(
                    CommentItem(
                        "21",
                        "31",
                        UserItem(
                            "12",
                            "noahbadomens",
                            "Noah Sebastian",
                            "12341234",
                            "email@gmail.com",
                            "Vocalist",
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
                        "Nice",
                        "15/2/2024 12:37"
                    )
                ),
                attachment = PhotoUrlItem(
                    "391",
                    "https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"
                ),
                isLiked = false,
                statistics = mutableListOf(
                    StatisticItem(StatisticType.LIKES, 21), StatisticItem(StatisticType.COMMENTS, 1)
                ),
            )
        )
        addPostItem(
            PostItem(
                id = "32",
                creatorName = "Normandie",
                creatorId = "74",
                creatorPicture = PhotoUrlItem(
                    "794",
                    "https://www.bringthenoiseuk.com/wp-content/uploads/normandie-band-2022.jpg"
                ),
                creatorType = CreatorType.BAND,
                text = "NEW ALBUM IS OUT!!!",
                date = "01/3/2024 19:37",
                comments = mutableListOf<CommentItem>(
                    CommentItem(
                        "21",
                        "32",
                        UserItem(
                            "14",
                            "dayseekerrory",
                            "Rory Rodriguez",
                            "12341234",
                            "4@gmail.com",
                            "Just a nice guy",
                            UserType.MUSICIAN,
                            PhotoUrlItem(
                                "194",
                                "https://images.genius.com/4321352a6796b4d618f8324ccdc68181.1000x1000x1.jpg"
                            ),
                            PhotoUrlItem(
                                "1104",
                                "https://www.bringthenoiseuk.com/wp-content/uploads/Dayseeker-2022-Credit-Amber-Paredes.jpg"
                            ),
                            UserSettingsItem(true, true)
                        ),
                        "Nice",
                        "01/3/2024 20:37"
                    ),
                    CommentItem(
                        "22",
                        "32",
                        UserItem(
                            "15",
                            "jessiecash5",
                            "Jessie Cash",
                            "12341234",
                            "5@gmail.com",
                            "Ghost Atlas / Erra",
                            UserType.MUSICIAN,
                            PhotoUrlItem(
                                "195",
                                "https://thenewfury.com/wp-content/uploads/2017/10/unnamed-1.jpg"
                            ),
                            PhotoUrlItem(
                                "1105",
                                "https://i.ytimg.com/vi/7VvYYohzaj0/maxresdefault.jpg"
                            ),
                            UserSettingsItem(true, true)
                        ),
                        "Amazing",
                        "01/3/2024 21:43"
                    )
                ),
                attachment = PhotoUrlItem(
                    "392",
                    "https://www.bringthenoiseuk.com/wp-content/uploads/normandie-band-2022.jpg"
                ),
                isLiked = false,
                statistics = mutableListOf(
                    StatisticItem(StatisticType.LIKES, 21), StatisticItem(StatisticType.COMMENTS, 2)
                ),
            )
        )
        addPostItem(
            PostItem(
                id = "33",
                creatorName = "КУЛЬТ",
                creatorId = "41",
                creatorPicture = PhotoUrlItem(
                    "491",
                    "https://avatars.mds.yandex.net/i?id=150e8ad466d96a519c0372d21be120ebcd4beaef-5329555-images-thumbs&n=13"
                ),
                creatorType = CreatorType.LOCATION,
                text = "ЕСЛИ ИЩЕТЕ ЛУЧШУЮ РЕПЕТИЦИОННУЮ БАЗУ В МСК ОБЯЗАТЕЛЬНО ПОСЕТИТЕ НАС",
                date = "15/2/2024 10:01",
                comments = mutableListOf<CommentItem>(
                ),
                attachment = PhotoUrlItem(
                    "393",
                    "https://avatars.mds.yandex.net/i?id=150e8ad466d96a519c0372d21be120ebcd4beaef-5329555-images-thumbs&n=13"
                ),
                isLiked = false,
                statistics = mutableListOf(
                    StatisticItem(StatisticType.LIKES, 21), StatisticItem(StatisticType.COMMENTS, 1)
                ),
            )
        )
    }

    companion object {

        private const val RETRY_TIMEOUT_MILLIS = 5L
    }

}