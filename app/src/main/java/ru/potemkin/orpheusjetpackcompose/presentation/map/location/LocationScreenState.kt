package ru.potemkin.orpheusjetpackcompose.presentation.map.location

import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.presentation.band.bandprofile.BandProfileScreenState

sealed class LocationScreenState {

    object Initial : LocationScreenState()
    object Loading : LocationScreenState()
    data class Location(
        val location: LocationItem,
        val posts: List<PostItem>,
        val nextDataIsLoading: Boolean = false
    ) : LocationScreenState()
}