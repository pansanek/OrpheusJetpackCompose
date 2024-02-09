package ru.potemkin.orpheusjetpackcompose.di


import dagger.BindsInstance
import dagger.Subcomponent
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.presentation.main.ViewModelFactory

@Subcomponent(
    modules = [
        UserProfileViewModelModule::class
    ]
)
interface UserProfileScreenComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance userItem: UserItem
        ): UserProfileScreenComponent
    }
}