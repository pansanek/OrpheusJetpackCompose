package ru.potemkin.orpheusjetpackcompose.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.potemkin.orpheusjetpackcompose.presentation.band.bandprofile.BandProfileViewModel

@Module
interface BandProfileViewModelModule {

    @IntoMap
    @ViewModelKey(BandProfileViewModel::class)
    @Binds
    fun bindBandProfileViewModel(viewModel: BandProfileViewModel): ViewModel
}