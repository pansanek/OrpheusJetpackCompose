package ru.potemkin.orpheusjetpackcompose.presentation.map.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetLocationPostsUseCase

class LocationViewModelFactory(
    private val location: LocationItem,
    private val getLocationPostsUseCase: GetLocationPostsUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LocationViewModel(location,getLocationPostsUseCase) as T
    }
}