package ru.potemkin.orpheusjetpackcompose.presentation.components.user_profile_comp

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.potemkin.orpheusjetpackcompose.R
import ru.potemkin.orpheusjetpackcompose.presentation.theme.Black
import ru.potemkin.orpheusjetpackcompose.presentation.theme.White

@Composable
fun UserProfileHeader(scrollState: LazyListState, topBarHeight: Dp) {
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
            painter = painterResource(id = R.drawable.sample2),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // User profile picture
            Image(
                painter = painterResource(id = R.drawable.sample2),
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
                contentScale = ContentScale.Crop)


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = { /* Действие для кнопки "Чат" */ },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                ) {
                    Icon(
                        imageVector = Icons.Default.Chat,
                        contentDescription = "Чат",
                        tint = Color.Black
                    )
                }

                Spacer(modifier = Modifier.width(64.dp))

                IconButton(
                    onClick = { /* Действие для кнопки "Лайкнуть" */ },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                ) {
                    Icon(
                        imageVector = Icons.Default.ThumbUp,
                        contentDescription = "Лайкнуть",
                        tint = Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Антон",
                style = MaterialTheme.typography.labelLarge,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))
            // User bio
            Text(
                text = "Просто крутой парень",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
        }



    }
}

