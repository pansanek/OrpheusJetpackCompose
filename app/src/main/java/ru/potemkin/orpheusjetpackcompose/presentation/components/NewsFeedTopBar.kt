package ru.potemkin.orpheusjetpackcompose.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DriveFileRenameOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorInfoItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorType
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.NotificationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.NotificationType
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.presentation.components.my_user_profile_comp.MenuItem
import ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.news.NewsFeedViewModel
import ru.potemkin.orpheusjetpackcompose.ui.theme.Black
import ru.potemkin.orpheusjetpackcompose.ui.theme.Grey
import ru.potemkin.orpheusjetpackcompose.ui.theme.LightBlack
import ru.potemkin.orpheusjetpackcompose.ui.theme.White

@Composable
fun NewsFeedTopBar(
    onPostCreateClickListener: (CreatorInfoItem) -> Unit,
    onDrawerClickListener: () -> Unit,
    viewModel:NewsFeedViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Новости", color = White)
                    Spacer(modifier = Modifier.weight(1f)) // Занимаем всю доступную ширину

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PostCreationButton(onPostCreateClickListener,viewModel)
                        SpacerWidth()
                        DrawerButton(onDrawerClickListener)
                    }

                }
            },
            backgroundColor = Black
        )


    }
}

@Composable
fun PostCreationButton(
    onPostCreateClickListener: (CreatorInfoItem) -> Unit,
    viewModel:NewsFeedViewModel
) {
    IconButton(
        onClick = {
            onPostCreateClickListener(viewModel.getCreatorInfo())
        },
        modifier = Modifier.size(48.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Создать пост",
            tint = White
        )
    }
}

@Composable
fun DrawerButton(onDrawerClickListener: () -> Unit) {
    IconButton(
        onClick = onDrawerClickListener,
        modifier = Modifier.size(48.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "Уведомления",
            tint = White
        )
    }
}

@Composable
fun NewsFeedDrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp, horizontal = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Уведомления", fontSize = 24.sp, color = White, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun NewsFeedDrawerBody(
    items: List<NotificationItem>,
    modifier: Modifier = Modifier,
    onUserClickListener: (UserItem) -> Unit,
    onBandClickListener: (BandItem) -> Unit,
    viewModel:NewsFeedViewModel
) {
    var selectedBand by remember { mutableStateOf<BandItem?>(null) }
    if (selectedBand != null) {
        selectedBand?.let { band ->
            CustomAlertDialog(
                band = band,
                onBandClickListener = {
                    if (band != null) {
                        onBandClickListener(band!!)
                    }
                },
                onDismiss = { selectedBand = null },
                onConfirm = {
                    viewModel.acceptBandInvitation(band)
                    selectedBand = null
                }
            )
        }
    }
    LazyColumn(modifier) {
        items(items) { item ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    AsyncImage(
                        model = item.fromUser.profile_picture.url,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .clickable {
                                onUserClickListener(item.fromUser)
                            },
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        androidx.compose.material3.Text(
                            text = item.title,
                            color = White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        androidx.compose.material3.Text(
                            text =
                            if (
                                item.type == NotificationType.LIKE
                            ) item.fromUser.login
                                    + item.contentDescription
                                    + (item.postItem?.date ?: " ")
                            else item.fromUser.login
                                    + item.contentDescription
                                    + (item.bandItem?.name ?: ""),
                            color = White,
                            modifier = Modifier.clickable {
                                if (item.bandItem != null) {
                                    selectedBand = item.bandItem
                                }
                            }
                        )
                    }
                }
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 3.dp,
                    color = LightBlack
                )
            }
        }
    }
}

@Composable
fun CustomAlertDialog(
    band: BandItem,
    onBandClickListener: (BandItem) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        ) {
            var graphicVisible by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) { graphicVisible = true }

            AnimatedVisibility(
                visible = graphicVisible,
                enter = expandVertically(
                    animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                    expandFrom = Alignment.CenterVertically,
                )
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(band.photo.url)
                                .crossfade(true)
                                .build(),
                            contentDescription = band.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                ,
                            contentScale = ContentScale.Crop
                        )
                    }
                    Column(modifier = Modifier.padding(16.dp)) {
                        // Заголовок
                        androidx.compose.material3.Text(
                            text = "Вас пригласили в группу",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            androidx.compose.material3.Text(
                                text = band.name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Icon(
                                imageVector = Icons.Default.Groups,
                                contentDescription = "Профиль группы",
                                tint = Black,
                                modifier = Modifier.clickable {
                                    onBandClickListener(band)
                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(32.dp))
                        // Кнопки действий
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextButton(
                                onClick = onDismiss,
                                colors = ButtonDefaults.textButtonColors(contentColor = Color.Black)
                            ) {
                                androidx.compose.material3.Text("Отказаться")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = onConfirm,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Black,
                                    contentColor = Color.White
                                ), // Установка черного цвета фона
                            ) {
                                androidx.compose.material3.Text("Принять")
                            }
                        }
                    }
                }
            }
        }
    }
}