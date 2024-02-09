package ru.potemkin.orpheusjetpackcompose.di


import dagger.BindsInstance
import dagger.Subcomponent
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.presentation.main.ViewModelFactory

@Subcomponent(
    modules = [
        BandProfileViewModelModule::class
    ]
)
interface BandProfileScreenComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance bandItem: BandItem
        ): BandProfileScreenComponent
    }
}