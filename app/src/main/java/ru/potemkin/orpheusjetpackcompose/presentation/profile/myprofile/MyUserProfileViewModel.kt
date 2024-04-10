package ru.potemkin.orpheusjetpackcompose.presentation.profile.myprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.domain.entities.MusicianItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.EditPostUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetUserPostsUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.EditMusicianUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.EditUserUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.GetMusicianUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.GetMyUserUseCase
import javax.inject.Inject

class MyUserProfileViewModel @Inject constructor(
    private val getMyUserUseCase: GetMyUserUseCase,
    private val getMyUserPostsUseCase: GetUserPostsUseCase,
    private val editUserUseCase: EditUserUseCase,
    private val editPostUseCase: EditPostUseCase,
    private val editMusicianItem: EditMusicianUseCase,
    private val getMusicianUseCase: GetMusicianUseCase
) : ViewModel() {

    private val initialState = MyUserProfileScreenState.Initial

    private val _screenState = MutableLiveData<MyUserProfileScreenState>(initialState)
    val screenState: LiveData<MyUserProfileScreenState> = _screenState



    val myUser = getMyUserUseCase.invoke()
    val postList = getMyUserPostsUseCase.invoke(myUser.id)
    init {
        _screenState.value = MyUserProfileScreenState.Loading
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
//            val users = repository.loadUsers()
//            val user = repository.getMyUser()
//            val feedPosts = repository.loadPosts(user.id)

            _screenState.value = MyUserProfileScreenState.User(user = myUser,posts = postList)
        }
    }

    fun changePrivacySettings(userSettings: UserSettingsItem){
        val oldUser = getMyUserUseCase.invoke()
        val newUser = UserItem(
            id = oldUser.id,
            login = oldUser.login,
            name =oldUser.name,
            password = oldUser.password,
            email = oldUser.email,
            about = oldUser.about,
            user_type= oldUser.user_type,
            profile_picture= PhotoUrlItem(
                id = oldUser.profile_picture.id,
                url = oldUser.profile_picture.url
            ),
            background_picture= PhotoUrlItem(
                id = oldUser.background_picture.id,
                url = oldUser.background_picture.url
            ),
            settings= userSettings
        )
        editUserUseCase.invoke(newUser)
    }
    fun changeUserProfile(oldProfile:UserItem,userName:String,userAbout:String, profilePictureUrl:String,backgroundPictureUrl:String){
        val newUser = UserItem(
            id = oldProfile.id,
            login = oldProfile.login,
            name = userName,
            password = oldProfile.password,
            email = oldProfile.email,
            about = userAbout,
            user_type= oldProfile.user_type,
            profile_picture= PhotoUrlItem(
                id = oldProfile.profile_picture.id,
                url = profilePictureUrl
            ),
            background_picture= PhotoUrlItem(
                id = oldProfile.background_picture.id,
                url = backgroundPictureUrl
            ),
            settings= oldProfile.settings,
        )
        if(oldProfile.user_type ==UserType.MUSICIAN){
            val oldMusician = getMusicianUseCase.invoke(oldProfile)
            editMusicianItem(
                MusicianItem(
                    id = oldMusician.id,
                    user = newUser,
                    genre = oldMusician.genre,
                    instrument = oldMusician.instrument
                )
            )
        }
        editUserUseCase.invoke(userItem = UserItem(
            id = oldProfile.id,
            login = oldProfile.login,
            name = userName,
            password = oldProfile.password,
            email = oldProfile.email,
            about = userAbout,
            user_type= oldProfile.user_type,
            profile_picture= PhotoUrlItem(
                id = oldProfile.profile_picture.id,
                url = profilePictureUrl
            ),
            background_picture= PhotoUrlItem(
                id = oldProfile.background_picture.id,
                url = backgroundPictureUrl
            ),
            settings= oldProfile.settings,
        ))
        changeUsersPosts(userName,profilePictureUrl)

    }

    private fun changeUsersPosts(userName: String, profilePictureUrl: String) {
        for(i in postList){
            i.creatorName = userName
            i.creatorPicture.url = profilePictureUrl
            editPostUseCase.invoke(i)
        }

    }


}