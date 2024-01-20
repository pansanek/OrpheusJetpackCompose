package ru.potemkin.orpheusjetpackcompose.presentation.screens.states

import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem

data class MapViewState(
    val selectedLocation: LocationItem? = null
)