package ru.potemkin.orpheusjetpackcompose.presentation.band.bandprofile

import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MusicianItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem


sealed class BandProfileScreenState {

    object Initial : BandProfileScreenState()

    object Loading : BandProfileScreenState()
    data class Band(
        val band: BandItem,
        val posts: List<PostItem>,
        val nextDataIsLoading: Boolean = false
    ) : BandProfileScreenState()
}