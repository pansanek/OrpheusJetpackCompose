package ru.potemkin.orpheusjetpackcompose.presentation.map.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.LocationRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorType

import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MusicianItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticType
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.domain.usecases.location_usecases.EditLocationUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.EditPostUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetLocationPostsUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetUserPostsUseCase
import javax.inject.Inject


class LocationViewModel @Inject constructor(
    location: LocationItem,
    getLocationPostsUseCase: GetLocationPostsUseCase,
    private val editLocationUseCase: EditLocationUseCase,
    private val editPostUseCase: EditPostUseCase,
) : ViewModel() {

    private val _screenState = MutableLiveData<LocationScreenState>(LocationScreenState.Initial)
    val screenState: LiveData<LocationScreenState> = _screenState

    val postList = getLocationPostsUseCase.invoke(location.id)
    init {
        loadLocation(location)
    }

    private fun loadLocation(
        location: LocationItem,
    ) {
        viewModelScope.launch {
            _screenState.value = LocationScreenState.Location(
                location = location,
                posts = postList
            )
        }
    }

    fun changeLocationProfile(
        oldProfile: LocationItem,
        locationName: String,
        locationAddress: String,
        locationAbout: String,
        profilePictureUrl: String
    ) {
        val newLocation = LocationItem(
            id = oldProfile.id,
            admin = oldProfile.admin,
            name = locationName,
            address = locationAddress,
            about = locationAbout,
            profilePicture = PhotoUrlItem(
                id = oldProfile.id,
                url = profilePictureUrl
            ),
            latitude = oldProfile.latitude,
            longitude = oldProfile.longitude,
        )
        editLocationUseCase.invoke(newLocation)

        changeLocationPosts(locationName, profilePictureUrl)

    }

    private fun changeLocationPosts(userName: String, profilePictureUrl: String) {
        for(i in postList){
            i.creatorName = userName
            i.creatorPicture.url = profilePictureUrl
            editPostUseCase.invoke(i)
        }

    }
}