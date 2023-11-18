package ru.potemkin.orpheusjetpackcompose.presentation.viewmodels

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ru.potemkin.orpheusjetpackcompose.data.repositories.LocationRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.data.states.MapViewState
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.usecases.location_usecases.GetLocationListUseCase
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val getLocationListUseCase: GetLocationListUseCase
): ViewModel() {

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