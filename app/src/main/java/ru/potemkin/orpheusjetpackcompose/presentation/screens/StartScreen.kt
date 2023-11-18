package ru.potemkin.orpheusjetpackcompose.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ru.potemkin.orpheusjetpackcompose.presentation.components.ButtonComponent
import ru.potemkin.orpheusjetpackcompose.presentation.components.SpacerWidth
import ru.potemkin.orpheusjetpackcompose.R
import ru.potemkin.orpheusjetpackcompose.navigation.LOG_SCREEN
import ru.potemkin.orpheusjetpackcompose.presentation.theme.Black
import ru.potemkin.orpheusjetpackcompose.presentation.theme.Green
import ru.potemkin.orpheusjetpackcompose.presentation.theme.OrpheusJetpackComposeTheme

@Composable
fun StartScreen(
    navHostController: NavHostController
) {
    val surfaceVisible = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Green)
    ) {
        AnimatedVisibility(
            visible = surfaceVisible.value,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it })
        ) {
            Image(
                painter = painterResource(id = R.drawable.harp), contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .clip(CircleShape)
                    .scale(0.5F)


            )

            Box(
                modifier = Modifier
                    .padding(top = 300.dp)
                    .align(Alignment.Center)

            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 40.dp)
                ) {
                    Text(
                        text = stringResource(R.string.stay_with_your_friends),
                        style = TextStyle(
                            fontSize = 36.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            ButtonComponent(
                modifier = Modifier
                    .padding(top = 500.dp)
                    .padding(20.dp)
                    .align(Alignment.BottomCenter)
                    .height(60.dp)
            ) {
                navHostController.navigate(LOG_SCREEN)
            }
        }

        LaunchedEffect(Unit) {
            surfaceVisible.value = true
        }
    }
}



