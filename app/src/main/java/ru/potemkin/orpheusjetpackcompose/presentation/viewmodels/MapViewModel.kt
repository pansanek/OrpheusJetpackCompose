package ru.potemkin.orpheusjetpackcompose.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ru.potemkin.orpheusjetpackcompose.data.repositories.LocationRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.data.states.MapViewState
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.usecases.location_usecases.GetLocationListUseCase

class MapViewModel : ViewModel() {

    private val repository = LocationRepositoryImpl

    private val getLocationListUseCase = GetLocationListUseCase(repository)

    val locationList = getLocationListUseCase.getLocationList()

    private val _state = mutableStateOf(MapViewState())

    val state: State<MapViewState> = _state

    fun selectLocation(location: LocationItem) {
        _state.value = _state.value.copy(selectedLocation = location)
    }

    fun clearSelectedLocation() {
        _state.value = _state.value.copy(selectedLocation = null)
    }

}