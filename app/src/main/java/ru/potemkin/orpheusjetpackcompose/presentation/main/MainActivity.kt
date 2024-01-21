package ru.potemkin.orpheusjetpackcompose.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.potemkin.orpheusjetpackcompose.navigation.MainNavigation
import ru.potemkin.orpheusjetpackcompose.ui.theme.OrpheusJetpackComposeTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy{
        (application as MainApplication).component
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
//        MapKitFactory.setApiKey("YOUR_API_KEY")
        setContent {
            OrpheusJetpackComposeTheme {
                MainNavigation(viewModelFactory)
            }
        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        MapKitFactory.getInstance().onDestroy()
//    }
}



