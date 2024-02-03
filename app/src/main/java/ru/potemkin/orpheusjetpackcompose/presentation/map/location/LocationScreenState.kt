package ru.potemkin.orpheusjetpackcompose.presentation.map.location

import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem

sealed class LocationScreenState {

    object Initial : LocationScreenState()

    data class Location(
        val location: LocationItem,
    ) : LocationScreenState()
}