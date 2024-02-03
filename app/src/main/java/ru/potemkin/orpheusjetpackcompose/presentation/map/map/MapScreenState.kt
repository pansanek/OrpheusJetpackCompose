package ru.potemkin.orpheusjetpackcompose.presentation.map.map

import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem

sealed class MapScreenState {

    object Initial : MapScreenState()

    object Loading : MapScreenState()
    data class Locations(
        val locations: List<LocationItem>
    ) : MapScreenState()
}