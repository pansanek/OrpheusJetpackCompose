package ru.potemkin.orpheusjetpackcompose.presentation.main

import android.app.Application
import ru.potemkin.orpheusjetpackcompose.di.ApplicationComponent
import ru.potemkin.orpheusjetpackcompose.di.DaggerApplicationComponent

class MainApplication:Application() {

    val component:ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}