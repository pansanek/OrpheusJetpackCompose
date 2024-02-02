package ru.potemkin.orpheusjetpackcompose.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.potemkin.orpheusjetpackcompose.presentation.main.MainActivity
import ru.potemkin.orpheusjetpackcompose.presentation.main.ViewModelFactory

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)

    fun getCommentsScreenComponentFactory(): CommentsScreenComponent.Factory

    fun getChatScreenComponentFactory(): ChatScreenComponent.Factory

    fun getViewModelFactory(): ViewModelFactory
    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}