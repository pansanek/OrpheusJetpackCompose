package ru.potemkin.orpheusjetpackcompose.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import ru.potemkin.orpheusjetpackcompose.R
import ru.potemkin.orpheusjetpackcompose.ui.theme.Black
import ru.potemkin.orpheusjetpackcompose.ui.theme.OrpheusJetpackComposeTheme

@Composable
fun LoadingComponent() {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.animation))
    var isPlaying by remember { mutableStateOf(true) }

    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isPlaying
    )

    LaunchedEffect(key1 = progress){
        if(progress ==0f){
            isPlaying = true
        }
        if(progress==1f){
            isPlaying=true
        }
    }
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        LottieAnimation(composition = composition,
            progress = {progress},
            modifier = Modifier.size(400.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingComponentPreview() {
 OrpheusJetpackComposeTheme {
     Box(modifier = Modifier.fillMaxSize().background(Black), contentAlignment = Alignment.Center
     ) {
        LoadingComponent()
     }
    }
}