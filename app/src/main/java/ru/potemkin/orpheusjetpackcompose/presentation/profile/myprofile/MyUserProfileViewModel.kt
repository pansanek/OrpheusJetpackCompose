package ru.potemkin.orpheusjetpackcompose.presentation.profile.myprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.UserRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorType
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticType
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import javax.inject.Inject

class MyUserProfileViewModel @Inject constructor() : ViewModel() {

    private val initialState = MyUserProfileScreenState.Initial

    private val _screenState = MutableLiveData<MyUserProfileScreenState>(initialState)
    val screenState: LiveData<MyUserProfileScreenState> = _screenState

    private val repository = UserRepositoryImpl()

    init {
        _screenState.value = MyUserProfileScreenState.Loading
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
//            val users = repository.loadUsers()
//            val user = repository.getMyUser()
//            val feedPosts = repository.loadPosts(user.id)

            val user = addUserMockData()
            val feedPosts = addPostMockData()
            _screenState.value = MyUserProfileScreenState.User(user = user,posts = feedPosts)
        }
    }



    fun addUserMockData():UserItem{
        val mockData =
            UserItem(
                "51bdc118-e76b-4372-8678-6822658cefed",
                "pansanek",
                "Sasha",
                "12341234",
                "email@gmail.com",
                "Hehe",
                UserType.MUSICIAN,
                PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://images6.fanpop.com/image/photos/38800000/-Matt-Nicholls-Upset-Magazine-Portrait-bring-me-the-horizon-38883120-1500-2250.jpg"),
                PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de4","https://i.pinimg.com/originals/06/67/9c/06679c2e2ae5aee8cf25eedc4bb41b98.jpg"),
                UserSettingsItem(true,true)
            )
        return mockData
    }
    fun addPostMockData():List<PostItem>{
        val mockData = mutableListOf<PostItem>(
            PostItem(
                id= "a9d28f2a-5eae-48bf-85f7-7c8dde3ec22c",
                creatorId = "a9d28f2a-5eae-48bf-85f7-7c8dde3ec23c",
                creatorName = "pansanek" ,
                creatorPicture = PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://images6.fanpop.com/image/photos/38800000/-Matt-Nicholls-Upset-Magazine-Portrait-bring-me-the-horizon-38883120-1500-2250.jpg"),
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
                            PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://images6.fanpop.com/image/photos/38800000/-Matt-Nicholls-Upset-Magazine-Portrait-bring-me-the-horizon-38883120-1500-2250.jpg"),
                            PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de4","https://i.pinimg.com/originals/06/67/9c/06679c2e2ae5aee8cf25eedc4bb41b98.jpg"),
                            UserSettingsItem(true,true)
                        ),
                        "Nice",
                        "01-07-21"
                    )
                ),
                attachment =  PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de5","https://cdn42.zvuk.com/pic?type=release&id=12958867&ext=jpg&size=1920x1920"),
                isLiked = true,
                statistics = mutableListOf(
                    StatisticItem(StatisticType.LIKES,21), StatisticItem(StatisticType.COMMENTS,1)
                ),
            ),
            PostItem(
                id= "a9d28f2a-5eae-48bf-85f7-7c8dde3ec23c",
                creatorId = "a9d28f2a-5eae-48bf-85f7-7c8dde3ec23c",
                creatorName = "pansanek" ,
                creatorPicture = PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://images6.fanpop.com/image/photos/38800000/-Matt-Nicholls-Upset-Magazine-Portrait-bring-me-the-horizon-38883120-1500-2250.jpg"),
                creatorType = CreatorType.USER,
                text = "AMO OUT NOW!!!",
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
                            PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://images6.fanpop.com/image/photos/38800000/-Matt-Nicholls-Upset-Magazine-Portrait-bring-me-the-horizon-38883120-1500-2250.jpg"),
                            PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de4","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                            UserSettingsItem(true,true)
                        ),
                        "Nice",
                        "01-02-24"
                    )
                ),
                attachment =  PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de5","https://cdn1.ozone.ru/multimedia/1026885091.jpg"),
                isLiked = true,
                statistics = mutableListOf(
                    StatisticItem(StatisticType.LIKES,21), StatisticItem(StatisticType.COMMENTS,1)
                ),
            ),
            PostItem(
                id= "a9d28f2a-5eae-48bf-85f7-7c8dde3ec23g",
                creatorId = "a9d28f2a-5eae-48bf-85f7-7c8dde3ec23c",
                creatorName = "pansanek" ,
                creatorPicture = PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://images6.fanpop.com/image/photos/38800000/-Matt-Nicholls-Upset-Magazine-Portrait-bring-me-the-horizon-38883120-1500-2250.jpg"),
                creatorType = CreatorType.USER,
                text = "gang",
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
                            PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://images6.fanpop.com/image/photos/38800000/-Matt-Nicholls-Upset-Magazine-Portrait-bring-me-the-horizon-38883120-1500-2250.jpg"),
                            PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de4","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                            UserSettingsItem(true,true)
                        ),
                        "Nice",
                        "01-02-24"
                    )
                ),
                attachment =  PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de5","https://cms.kerrang.com/images/Bring-Me-The-Horizon-December-2021-promo.jpg"),
                isLiked = true,
                statistics = mutableListOf(
                    StatisticItem(StatisticType.LIKES,14), StatisticItem(StatisticType.COMMENTS,1)
                ),
            )
        )
        return mockData
    }





}