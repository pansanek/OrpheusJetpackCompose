package ru.potemkin.orpheusjetpackcompose.presentation.map.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.LocationRepositoryImpl
import javax.inject.Inject

class MapViewModel @Inject constructor() : ViewModel() {


    private val initialState = MapScreenState.Initial

    private val _screenState = MutableLiveData<MapScreenState>(initialState)
    val screenState: LiveData<MapScreenState> = _screenState

    private val repository = LocationRepositoryImpl()

    init {
        _screenState.value = MapScreenState.Loading
        loadLocations()
    }

    private fun loadLocations() {
        viewModelScope.launch {
            val locations = repository.loadLocations()
            _screenState.value = MapScreenState.Locations(locations = locations)
        }
    }

}