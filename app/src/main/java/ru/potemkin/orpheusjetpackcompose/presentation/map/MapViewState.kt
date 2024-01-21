package ru.potemkin.orpheusjetpackcompose.presentation.map

import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem

data class MapViewState(
    val selectedLocation: LocationItem? = null
)