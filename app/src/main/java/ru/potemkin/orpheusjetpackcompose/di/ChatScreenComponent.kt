package ru.potemkin.orpheusjetpackcompose.di


import dagger.BindsInstance
import dagger.Subcomponent
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.presentation.main.ViewModelFactory

@Subcomponent(
    modules = [
        ChatViewModelModule::class
    ]
)
interface ChatScreenComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance chatItem: ChatItem
        ): ChatScreenComponent
    }
}