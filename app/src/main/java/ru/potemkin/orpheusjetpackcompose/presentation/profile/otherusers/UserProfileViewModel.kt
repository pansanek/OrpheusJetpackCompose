package ru.potemkin.orpheusjetpackcompose.presentation.profile.otherusers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.UserRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticType
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import javax.inject.Inject

class UserProfileViewModel @Inject constructor(userItem: UserItem) : ViewModel() {

    private val initialState = UserProfileScreenState.Initial

    private val _screenState = MutableLiveData<UserProfileScreenState>(initialState)
    val screenState: LiveData<UserProfileScreenState> = _screenState

    private val repository = UserRepositoryImpl()

    init {
        _screenState.value = UserProfileScreenState.Loading
        loadPosts(userItem)
    }

    private fun loadPosts(user:UserItem) {
        viewModelScope.launch {
//            val feedPosts = repository.loadPosts(user.id)
            val feedPosts = addPostMockData()
            _screenState.value = UserProfileScreenState.User(
                user = user,
                posts = feedPosts,
                location = null,//addLocationMockData(),
                bands = addBandMockData())
        }
    }

    fun addBandMockData():List<BandItem>{
        val mockData = mutableListOf<BandItem>(
            BandItem(
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
            )
        )
        return mockData
    }
    fun addLocationMockData():LocationItem{
        val mockData =
            LocationItem(
                id= "a9d28f2a-5eae-48bf-85f7-7c8dde3ec22c",
                admin = UserItem(
                    "51bdc118-e76b-4372-8678-6822658cefed",
                    "pansanek",
                    "Sasha",
                    "12341234",
                    "email@gmail.com",
                    "Hehe",
                    UserType.ADMINISTRATOR,
                    PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://img-fotki.yandex.ru/get/5803/12042645.1d/0_965fd_fcd89bb9_orig.jpg"),
                    PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de4","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                    UserSettingsItem(true,true)
                ),
                about = "Настоящий храм творчества и оплот музыкальной КУЛЬТуры, созданный музыкантами для музыкантов.",
                name = "КУЛЬТ",
                address = "Электрозаводская улица, 21, Москва",
                profilePicture = PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fc5325235234","https://avatars.mds.yandex.net/i?id=150e8ad466d96a519c0372d21be120ebcd4beaef-5329555-images-thumbs&n=13"),
                latitude = 55.786505,
                longitude = 37.704143
            )
        return mockData
    }
    fun addPostMockData():List<PostItem>{
        val mockData = mutableListOf<PostItem>(
            PostItem(
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
        )
        return mockData
    }



}