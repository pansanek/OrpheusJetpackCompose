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

    override suspend fun changeLikeStatus(postItemId: String, userId:String) {
        val oldElement = getPostItem(postItemId)
        var likes = mutableListOf<String>()
        for (i in oldElement.likes) likes.add(i)
        _postItems.remove(oldElement)
        if(oldElement.isLiked == false) {
            likes.add(userId)
            oldElement.isLiked = true
            oldElement.statistics.get(0).count += 1
            addPostItem(oldElement)
        }
        else{
            likes.remove(userId)
            oldElement.isLiked = false
            oldElement.statistics.get(0).count -= 1
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
                text = "ВЫШЕЛ НОВЫЙ АЛЬБОМ!!!",
                date = "01/3/2024 19:37",
                comments = mutableListOf<CommentItem>(
                    CommentItem(
                        "21",
                        "32",
                        UserItem(
                            "19",
                            "landontewers",
                            "Лэндон Тьюерс",
                            "12341234",
                            "9@gmail.com",
                            "Много пою и кричу",
                            UserType.MUSICIAN,
                            PhotoUrlItem(
                                "199",
                                "https://sun9-25.userapi.com/impf/c840320/v840320259/36208/h5GVeRP9URM.jpg?size=640x640&quality=96&sign=f5307f49e081c58b7cbb3bbb4680efb6&c_uniq_tag=FbHPADgjU38jiYwFHjugwpbBeRJbDbBXfs4fCfTv3rk&type=album"
                            ),
                            PhotoUrlItem(
                                "1109",
                                "https://i0.wp.com/distortedsoundmag.com/wp-content/uploads/2017/11/TPIY_Dispose_3000px_600dpi_RGB.jpg?w=3000&ssl=1"
                            ),
                            UserSettingsItem(true, true)
                        ),
                        "СУПЕР КРУТО!",
                        "01/3/2024 20:37"
                    ),
                    CommentItem(
                        "22",
                        "32",
                        UserItem(
                            "15",
                            "jessiecash5",
                            "Джесси Кэш",
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
                likes= listOf<String>("11","12"),
                isLiked = true,
                statistics = mutableListOf(
                    StatisticItem(StatisticType.LIKES, 2), StatisticItem(StatisticType.COMMENTS, 2)
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
                likes= listOf<String>("14","15"),
                statistics = mutableListOf(
                    StatisticItem(StatisticType.LIKES, 2), StatisticItem(StatisticType.COMMENTS, 1)
                ),
            )
        )
        addPostItem(
            PostItem(
                id = "34",
                creatorName = "noahbadomens",
                creatorId = "12",
                creatorPicture = PhotoUrlItem(
                    "192",
                    "https://i.pinimg.com/originals/7a/bd/00/7abd00f199dff4ec1364663ce0b45ea3.jpg"
                ),
                creatorType = CreatorType.USER,
                text = "Всем привет!",
                date = "15/2/2024 10:01",
                comments = mutableListOf<CommentItem>(

                ),
                likes= listOf<String>(),
                attachment = PhotoUrlItem(
                    "391",
                    "https://i.pinimg.com/originals/c2/63/df/c263dfd3846e856bf14f7da077808a9e.jpg"
                ),
                isLiked = false,
                statistics = mutableListOf(
                    StatisticItem(StatisticType.LIKES, 0), StatisticItem(StatisticType.COMMENTS, 1)
                ),
            )
        )
    }

    companion object {

        private const val RETRY_TIMEOUT_MILLIS = 5L
    }

}