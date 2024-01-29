package ru.potemkin.orpheusjetpackcompose.presentation.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.potemkin.orpheusjetpackcompose.R
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.presentation.components.ButtonComponent
import ru.potemkin.orpheusjetpackcompose.ui.theme.Green

@Composable
fun StartScreen(
    paddingValues: PaddingValues,
    onAuthClickListener: () -> Unit,
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

            }
        }

        LaunchedEffect(Unit) {
            surfaceVisible.value = true
        }
    }
}



