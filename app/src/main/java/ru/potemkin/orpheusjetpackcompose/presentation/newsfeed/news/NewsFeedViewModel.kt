package ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.PostRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.NotificationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.NotificationType
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticType
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import javax.inject.Inject

class NewsFeedViewModel @Inject constructor() : ViewModel() {

    private val initialState = NewsFeedScreenState.Initial

    private val _screenState = MutableLiveData<NewsFeedScreenState>(initialState)
    val screenState: LiveData<NewsFeedScreenState> = _screenState

    private val repository = PostRepositoryImpl()

    init {
        _screenState.value = NewsFeedScreenState.Loading
        loadRecommendations()
    }

    private fun loadRecommendations() {
        viewModelScope.launch {
//            val feedPosts = repository.loadRecommendations()
            val feedPosts = addMockData()
            _screenState.value = NewsFeedScreenState.Posts(posts = feedPosts, notifications = addNotificationMockData())
        }
    }

    fun addNotificationMockData():List<NotificationItem>{
        val mockData = mutableListOf<NotificationItem>(
            NotificationItem(
                id= "1",
                type = NotificationType.LIKE,
                bandItem = null,
                contentDescription = " оценил вашу запись от ",
                title = "Ваш пост оценили",
                fromUser = UserItem(
                    "51bdc118-e76b-4372-8678-6822658cefed",
                    "noahbadomens",
                    "Noah Sebastian",
                    "12341234",
                    "email@gmail.com",
                    "Vocalist for Bad Omens",
                    UserType.MUSICIAN,
                    PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://i.pinimg.com/originals/7a/bd/00/7abd00f199dff4ec1364663ce0b45ea3.jpg"),
                    PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de4","https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"),
                    UserSettingsItem(true,true)
                ),
                toUser = UserItem(
                    "51bdc118-e76b-4372-8678-6822658cefed",
                    "pansanek",
                    "Sasha",
                    "12341234",
                    "email@gmail.com",
                    "Hehe",
                    UserType.MUSICIAN,
                    PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                    PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de4","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                    UserSettingsItem(true,true)
                ),
                date = "06-03-24",
                postItem = PostItem(
                    id= "a9d28f2a-5eae-48bf-85f7-7c8dde3ec22c",
                    creatorId = "a9d28f2a-5eae-48bf-85f7-7c8dde3ec23c",
                    creatorName = "pansanek" ,
                    creatorPicture = PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                    creatorType = "MUSICIAN",
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
                                PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                                PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de4","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                                UserSettingsItem(true,true)
                            ),
                            "Nice",
                            "01-07-21"
                        )
                    ),
                    attachment =  PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de5","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                    isLiked = true,
                    statistics = mutableListOf(
                        StatisticItem(StatisticType.LIKES,21), StatisticItem(StatisticType.COMMENTS,1)
                    ),
                )
            ),
            NotificationItem(
                id= "1",
                type = NotificationType.BAND_INVITE,
                bandItem = BandItem(
                    "51bdc118-e76b-4372-8678-6822658cefed",
                    "Bad Omens",
                    listOf(
                        UserItem(
                            "51bdc118-e76b-4372-8678-6822658cefed",
                            "noahbadomens",
                            "Noah Sebastian",
                            "12341234",
                            "email@gmail.com",
                            "Vocalist for Bad Omens",
                            UserType.MUSICIAN,
                            PhotoUrlItem(
                                "b59ae42e-8859-441a-9a3a-2fca1b784de3",
                                "https://i.pinimg.com/originals/7a/bd/00/7abd00f199dff4ec1364663ce0b45ea3.jpg"
                            ),
                            PhotoUrlItem(
                                "b59ae42e-8859-441a-9a3a-2fca1b784de4",
                                "https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"
                            ),
                            UserSettingsItem(true, true)
                        ),
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
                                "https://images6.fanpop.com/image/photos/38800000/-Matt-Nicholls-Upset-Magazine-Portrait-bring-me-the-horizon-38883120-1500-2250.jpg"
                            ),
                            PhotoUrlItem(
                                "b59ae42e-8859-441a-9a3a-2fca1b784de4",
                                "https://i.pinimg.com/originals/06/67/9c/06679c2e2ae5aee8cf25eedc4bb41b98.jpg"
                            ),
                            UserSettingsItem(true, true)
                        )
                    ),
                    "Metalcore",
                    PhotoUrlItem(
                        "b59ae42e-8859-441a-9a3a-2fca1b784de4",
                        "https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"
                    )
                ),
                contentDescription = " пригласил вас в группу ",
                title = "Вас пригласили в группу",
                fromUser = UserItem(
                    "51bdc118-e76b-4372-8678-6822658cefed",
                    "noahbadomens",
                    "Noah Sebastian",
                    "12341234",
                    "email@gmail.com",
                    "Vocalist for Bad Omens",
                    UserType.MUSICIAN,
                    PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://i.pinimg.com/originals/7a/bd/00/7abd00f199dff4ec1364663ce0b45ea3.jpg"),
                    PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de4","https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"),
                    UserSettingsItem(true,true)
                ),
                toUser = UserItem(
                    "51bdc118-e76b-4372-8678-6822658cefed",
                    "pansanek",
                    "Sasha",
                    "12341234",
                    "email@gmail.com",
                    "Hehe",
                    UserType.MUSICIAN,
                    PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                    PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de4","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                    UserSettingsItem(true,true)
                ),
                postItem = null,
                date = "06-03-24"
            ),

        )
        return mockData
    }
    fun addMockData():List<PostItem>{
        val mockData = mutableListOf<PostItem>(
            PostItem(
                id= "a9d28f2a-5eae-48bf-85f7-7c8dde3ec22c",
                creatorName = "pansanek" ,
                creatorId = "a9d28f2a-5eae-48bf-85f7-7c8dde3ec23c",
                creatorPicture = PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                creatorType = "MUSICIAN",
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
                        PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                        PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de4","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                        UserSettingsItem(true,true)
                    ),
                    "Nice",
                    "01-07-21"
                )
                ),
                attachment =  PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de5","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                isLiked = true,
                statistics = mutableListOf(
                    StatisticItem(StatisticType.LIKES,21), StatisticItem(StatisticType.COMMENTS,1)
                ),
            ),
            PostItem(
                id= "a9d28f2a-5eae-48bf-85f7-7c8dde3ec23c",
                creatorName = "Dealer" ,
                creatorId = "a9d28f2a-5eae-48bf-85f7-7c8dde3ec23c",
                creatorPicture = PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://www.noecho.net/uploads/wysiwyg/56398448_2377037402530657_3620978213045403648_o.jpg"),
                creatorType = "BAND",
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
                            PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                            PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de4","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                            UserSettingsItem(true,true)
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
                            PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://images6.fanpop.com/image/photos/38800000/-Matt-Nicholls-Upset-Magazine-Portrait-bring-me-the-horizon-38883120-1500-2250.jpg"),
                            PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de4","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                            UserSettingsItem(true,true)
                        ),
                        "Nice",
                        "01-02-24"
                    )
                ),
                attachment =  PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de5","https://thenewfury.com/wp-content/uploads/2020/06/71892285_2515240622043667_2289500113191567360_o-e1592843931224.jpg"),
                isLiked = true,
                statistics = mutableListOf(
                    StatisticItem(StatisticType.LIKES,21), StatisticItem(StatisticType.COMMENTS,2)
                ),
            )
        )
        return mockData
    }

//    fun changeLikeStatus(feedPost: PostItem) {
//        viewModelScope.launch {
//            repository.changeLikeStatus(feedPost)
//            _screenState.value = NewsFeedScreenState.Posts(posts = repository.postList)
//        }
//    }

}