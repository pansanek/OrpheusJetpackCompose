package ru.potemkin.orpheusjetpackcompose.presentation.profile.otherusers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.UserRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
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
            _screenState.value = UserProfileScreenState.User(user = user,posts = feedPosts)
        }
    }

    fun addUserMockData():List<UserItem>{
        val mockData = mutableListOf<UserItem>(
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
            )
        )
        return mockData
    }
    fun addPostMockData():List<PostItem>{
        val mockData = mutableListOf<PostItem>(
            PostItem(
                id= "a9d28f2a-5eae-48bf-85f7-7c8dde3ec22c",
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