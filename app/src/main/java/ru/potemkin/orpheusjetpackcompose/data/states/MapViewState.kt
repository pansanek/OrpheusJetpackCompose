package ru.potemkin.orpheusjetpackcompose.data.states

import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem

data class MapViewState(
    val selectedLocation: LocationItem? = null
)