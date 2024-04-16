package ru.potemkin.orpheusjetpackcompose.presentation.profile.myprofile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.domain.entities.MusicianItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.EditPostUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetPostListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.EditMusicianUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.EditUserUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.GetMusiciansListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.GetMyUserUseCase
import javax.inject.Inject

class MyUserProfileViewModel @Inject constructor(
    private val getMyUserUseCase: GetMyUserUseCase,
    private val editUserUseCase: EditUserUseCase,
    private val editPostUseCase: EditPostUseCase,
    private val editMusicianItem: EditMusicianUseCase,
    private val getPostListUseCase: GetPostListUseCase,
    private val getMusiciansListUseCase: GetMusiciansListUseCase
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("ViewModel", "Exception caught by exception handler")
    }
    val myUser = getMyUserUseCase.invoke()

    val postListFlow = getPostListUseCase.invoke()

    val screenState = postListFlow
        .map { MyUserProfileScreenState.User(user = myUser,posts = it.filter { it.creatorId == myUser.id }) as MyUserProfileScreenState }
        .onStart { emit(MyUserProfileScreenState.Loading) }



    fun changePrivacySettings(userSettings: UserSettingsItem){
        viewModelScope.launch(exceptionHandler) {
            val oldUser = getMyUserUseCase.invoke()
            val newUser = UserItem(
                id = oldUser.id,
                login = oldUser.login,
                name = oldUser.name,
                password = oldUser.password,
                email = oldUser.email,
                about = oldUser.about,
                user_type = oldUser.user_type,
                profile_picture = PhotoUrlItem(
                    id = oldUser.profile_picture.id,
                    url = oldUser.profile_picture.url
                ),
                background_picture = PhotoUrlItem(
                    id = oldUser.background_picture.id,
                    url = oldUser.background_picture.url
                ),
                settings = userSettings
            )
            editUserUseCase.invoke(newUser)
        }
    }
     fun changeUserProfile(oldProfile:UserItem, userName:String, userAbout:String, profilePictureUrl:String, backgroundPictureUrl:String){
        viewModelScope.launch(exceptionHandler) {
            val newUser = UserItem(
                id = oldProfile.id,
                login = oldProfile.login,
                name = userName,
                password = oldProfile.password,
                email = oldProfile.email,
                about = userAbout,
                user_type = oldProfile.user_type,
                profile_picture = PhotoUrlItem(
                    id = oldProfile.profile_picture.id,
                    url = profilePictureUrl
                ),
                background_picture = PhotoUrlItem(
                    id = oldProfile.background_picture.id,
                    url = backgroundPictureUrl
                ),
                settings = oldProfile.settings,
            )
            if (oldProfile.user_type == UserType.MUSICIAN) {
                val oldMusician = getMusiciansListUseCase.invoke().filter { musicianItems ->
                    musicianItems.any() {musician -> musician.user.id == oldProfile.id}
                }
                val musician = oldMusician.first().get(0)
                editMusicianItem(
                    MusicianItem(
                        id = musician.id,
                        user = newUser,
                        genre = musician.genre,
                        instrument = musician.instrument
                    )
                )
            }
            editUserUseCase.invoke(
                userItem = UserItem(
                    id = oldProfile.id,
                    login = oldProfile.login,
                    name = userName,
                    password = oldProfile.password,
                    email = oldProfile.email,
                    about = userAbout,
                    user_type = oldProfile.user_type,
                    profile_picture = PhotoUrlItem(
                        id = oldProfile.profile_picture.id,
                        url = profilePictureUrl
                    ),
                    background_picture = PhotoUrlItem(
                        id = oldProfile.background_picture.id,
                        url = backgroundPictureUrl
                    ),
                    settings = oldProfile.settings,
                )
            )
            changeUsersPosts(profilePictureUrl)
        }

    }

    private  fun changeUsersPosts(profilePictureUrl: String) {
        viewModelScope.launch(exceptionHandler) {
            for (i in postListFlow.value) {
                if(i.creatorId == myUser.id) {
                    i.creatorPicture.url = profilePictureUrl
                    editPostUseCase.invoke(i)
                }
            }
        }

    }


}