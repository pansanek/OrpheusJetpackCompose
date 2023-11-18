package ru.potemkin.orpheusjetpackcompose.di

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.potemkin.orpheusjetpackcompose.presentation.MainActivity

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}