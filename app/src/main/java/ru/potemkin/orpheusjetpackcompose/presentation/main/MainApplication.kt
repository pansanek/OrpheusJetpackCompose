package ru.potemkin.orpheusjetpackcompose.presentation.main

import android.app.Application
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import ru.potemkin.orpheusjetpackcompose.di.ApplicationComponent
import ru.potemkin.orpheusjetpackcompose.di.DaggerApplicationComponent

class MainApplication:Application() {

    val component:ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}

@Composable
fun getApplicationComponent(): ApplicationComponent {
    Log.d("RECOMPOSITION_TAG", "getApplicationComponent")
    return (LocalContext.current.applicationContext as MainApplication).component
}