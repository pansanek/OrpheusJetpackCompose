package ru.potemkin.orpheusjetpackcompose.di


import dagger.BindsInstance
import dagger.Subcomponent
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorInfoItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.presentation.main.ViewModelFactory

@Subcomponent(
    modules = [
        PostCreationViewModelModule::class
    ]
)
interface PostCreationScreenComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance creatorInfoItem: CreatorInfoItem
        ): PostCreationScreenComponent
    }
}