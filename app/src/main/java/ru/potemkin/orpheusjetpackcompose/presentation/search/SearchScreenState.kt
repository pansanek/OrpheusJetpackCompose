package ru.potemkin.orpheusjetpackcompose.presentation.search

import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MusicianItem


sealed class SearchScreenState {

    object Initial : SearchScreenState()

    object Loading : SearchScreenState()
    data class Finds(
        val bands: List<BandItem>,
        val musicians: List<MusicianItem>,
        val nextDataIsLoading: Boolean = false
    ) : SearchScreenState()
}