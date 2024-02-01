package ru.potemkin.orpheusjetpackcompose.presentation.map

import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem

sealed class MapScreenState {

    object Initial : MapScreenState()

    data class Locations(
        val locations: List<LocationItem>
    ) : MapScreenState()
}