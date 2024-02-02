package ru.potemkin.orpheusjetpackcompose.presentation.map.map

import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.news.NewsFeedScreenState

sealed class MapScreenState {

    object Initial : MapScreenState()

    object Loading : MapScreenState()
    data class Locations(
        val locations: List<LocationItem>
    ) : MapScreenState()
}