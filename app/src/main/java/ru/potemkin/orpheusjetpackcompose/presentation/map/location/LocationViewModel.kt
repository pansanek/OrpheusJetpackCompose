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
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticType
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetLocationPostsUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetUserPostsUseCase
import javax.inject.Inject


class LocationViewModel @Inject constructor(
    location: LocationItem,
    getLocationPostsUseCase: GetLocationPostsUseCase
) : ViewModel() {

    private val _screenState = MutableLiveData<LocationScreenState>(LocationScreenState.Initial)
    val screenState: LiveData<LocationScreenState> = _screenState

    init {
        loadLocation(location,getLocationPostsUseCase)
    }

    private fun loadLocation(location: LocationItem,
                             getLocationPostsUseCase: GetLocationPostsUseCase) {
        viewModelScope.launch {
            _screenState.value = LocationScreenState.Location(
                location = location,
                posts = getLocationPostsUseCase.invoke(location.id)
            )
        }
    }


}