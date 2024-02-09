package ru.potemkin.orpheusjetpackcompose.di


import dagger.BindsInstance
import dagger.Subcomponent
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.presentation.main.ViewModelFactory

@Subcomponent(
    modules = [
        LocationViewModelModule::class
    ]
)
interface LocationScreenComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance locationItem: LocationItem
        ): LocationScreenComponent
    }
}