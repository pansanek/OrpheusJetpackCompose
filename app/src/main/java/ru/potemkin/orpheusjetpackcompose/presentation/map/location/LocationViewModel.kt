package ru.potemkin.orpheusjetpackcompose.presentation.map.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.LocationRepositoryImpl

import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import javax.inject.Inject


class LocationViewModel @Inject constructor(
    location: LocationItem,
) : ViewModel() {

    private val repository = LocationRepositoryImpl()

    private val _screenState = MutableLiveData<LocationScreenState>(LocationScreenState.Initial)
    val screenState: LiveData<LocationScreenState> = _screenState

    init {
        loadLocation(location)
    }

    private fun loadLocation(location: LocationItem) {
        viewModelScope.launch {
            _screenState.value = LocationScreenState.Location(
                location = location,
            )
        }
    }
}