package ru.potemkin.orpheusjetpackcompose.presentation.components.my_user_profile_comp

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import coil.compose.AsyncImage
import ru.potemkin.orpheusjetpackcompose.R
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.ui.theme.*

@Composable
fun MyUserProfileHeader(
    user:UserItem,
    scrollState: LazyListState,
    topBarHeight: Dp,
    onBandListClickListener: () -> Unit
) {
    // При скролле уменьшаем высоту Header и делаем его непрозрачным
    val headerHeight by animateDpAsState(
        targetValue = if (scrollState.firstVisibleItemIndex > 0) 0.dp else 200.dp
    )
    val headerAlpha by animateFloatAsState(
        targetValue = if (scrollState.firstVisibleItemIndex > 0) 0f else 1f
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(headerHeight)
            .graphicsLayer(alpha = headerAlpha)
            .background(Black)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(bottomEndPercent = 10, bottomStartPercent = 10),
            color = Black
        ) {
            AsyncImage(
                model = user.background_picture.url,
                modifier = Modifier.fillMaxSize(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // User profile picture
                AsyncImage(
                    model = user.profile_picture.url,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(4.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = user.about,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White
                )
            }
        }

    }
}

