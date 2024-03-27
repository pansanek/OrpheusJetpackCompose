package ru.potemkin.orpheusjetpackcompose.presentation.map.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.LocationRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticType
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.domain.usecases.location_usecases.GetLocationListUseCase
import javax.inject.Inject

class MapViewModel @Inject constructor(
    getLocationListUseCase: GetLocationListUseCase
) : ViewModel() {


    private val initialState = MapScreenState.Initial

    private val _screenState = MutableLiveData<MapScreenState>(initialState)
    val screenState: LiveData<MapScreenState> = _screenState

    val locations = getLocationListUseCase.invoke()

    init {
        _screenState.value = MapScreenState.Loading
        loadLocations()
    }

    private fun loadLocations() {
        viewModelScope.launch {
            _screenState.value = MapScreenState.Locations(locations = locations)
        }
    }


}