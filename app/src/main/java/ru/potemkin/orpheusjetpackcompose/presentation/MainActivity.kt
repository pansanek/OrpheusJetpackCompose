package ru.potemkin.orpheusjetpackcompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.potemkin.orpheusjetpackcompose.navigation.MainNavigation
import ru.potemkin.orpheusjetpackcompose.presentation.components.ButtonComponent
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



