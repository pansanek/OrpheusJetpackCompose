package ru.potemkin.orpheusjetpackcompose.presentation.components.profile_comp

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.potemkin.orpheusjetpackcompose.R
import ru.potemkin.orpheusjetpackcompose.presentation.components.ButtonComponent
import ru.potemkin.orpheusjetpackcompose.presentation.theme.Black
import ru.potemkin.orpheusjetpackcompose.presentation.theme.White

@Composable
fun ProfileHeader(scrollState: LazyListState, topBarHeight: Dp) {
    // При скролле уменьшаем высоту Header и делаем его непрозрачным
    val headerHeight by animateDpAsState(
        targetValue = if (scrollState.firstVisibleItemIndex > 0) 0.dp else 300.dp
    )
    val headerAlpha by animateFloatAsState(
        targetValue = if (scrollState.firstVisibleItemIndex > 0) 0f else 1f
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(headerHeight)
            .graphicsLayer(alpha = headerAlpha)
    ) {
        Image(
            painter = painterResource(id = R.drawable.sample9),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            // User profile picture
            Image(
                painter = painterResource(id = R.drawable.sample4),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .padding(4.dp)
                    .clip(MaterialTheme.shapes.small)
                    .border(
                        width = 2.dp,
                        color = White,
                        shape = MaterialTheme.shapes.small
                    ),
                contentScale = ContentScale.Crop
            )


            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Саша",
                style = MaterialTheme.typography.labelLarge,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))
            // User bio
            Text(
                text = "реальный кабан",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

        }
    }
}