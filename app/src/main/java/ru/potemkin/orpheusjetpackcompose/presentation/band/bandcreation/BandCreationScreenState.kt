package ru.potemkin.orpheusjetpackcompose.presentation.band.bandcreation

import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem


sealed class BandCreationScreenState {

    object Initial : BandCreationScreenState()

    object Loading : BandCreationScreenState()
    data class Bands(
        val bands: List<BandItem>,
        val nextDataIsLoading: Boolean = false
    ) : BandCreationScreenState()
}