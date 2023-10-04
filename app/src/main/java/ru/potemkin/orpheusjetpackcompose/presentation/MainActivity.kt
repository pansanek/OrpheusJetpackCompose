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
import ru.potemkin.orpheusjetpackcompose.presentation.components.ButtonComponent
import ru.potemkin.orpheusjetpackcompose.presentation.navigation.MainNavigation
import ru.potemkin.orpheusjetpackcompose.presentation.theme.OrpheusJetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        MapKitFactory.setApiKey("YOUR_API_KEY")
        setContent {
            OrpheusJetpackComposeTheme {
                MainNavigation()
            }
        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        MapKitFactory.getInstance().onDestroy()
//    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OrpheusJetpackComposeTheme {
        MainNavigation()
    }
}