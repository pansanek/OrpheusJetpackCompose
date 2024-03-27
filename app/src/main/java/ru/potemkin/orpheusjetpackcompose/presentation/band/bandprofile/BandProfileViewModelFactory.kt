package ru.potemkin.orpheusjetpackcompose.presentation.band.bandprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetBandPostsUseCase

class BandProfileViewModelFactory(
    private val band: BandItem,
    private val getBandPostsUseCase: GetBandPostsUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BandProfileViewModel(band,getBandPostsUseCase) as T
    }
}