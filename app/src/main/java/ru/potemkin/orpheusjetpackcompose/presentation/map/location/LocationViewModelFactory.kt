package ru.potemkin.orpheusjetpackcompose.presentation.map.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem

class LocationViewModelFactory(
    private val location: LocationItem,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LocationViewModel(location) as T
    }
}