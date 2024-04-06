package ru.potemkin.orpheusjetpackcompose.presentation.components.profile_comp

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.PersonAdd
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.presentation.components.CustomAlertDialog
import ru.potemkin.orpheusjetpackcompose.presentation.components.my_user_profile_comp.DrawerButton
import ru.potemkin.orpheusjetpackcompose.presentation.profile.otherusers.UserProfileViewModel
import ru.potemkin.orpheusjetpackcompose.ui.theme.Black
import ru.potemkin.orpheusjetpackcompose.ui.theme.LightBlack
import ru.potemkin.orpheusjetpackcompose.ui.theme.White

@Composable
fun ProfileTopBar(onBackPressed: () -> Unit, userItem: UserItem, viewModel: UserProfileViewModel) {
    var inviteUser by remember { mutableStateOf<UserItem?>(null) }
    Column(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = userItem.login, color = White)
                    Spacer(modifier = Modifier.weight(1f)) // Занимаем всю доступную ширину
                    InviteButton { inviteUser = userItem }
                }
            },
            navigationIcon = {
                IconButton(onClick = { onBackPressed() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Назад",
                        tint = White
                    )
                }
            },
            backgroundColor = Black
        )
    }
    if (inviteUser != null) {
        inviteUser?.let { user ->
            InviteAlertDialog(
                viewModel = viewModel,
                onDismiss = { inviteUser = null },
                onConfirm = {
                    viewModel.sendBandInvitation(it, user)
                    inviteUser = null
                }
            )
        }
    }
}


@Composable
fun InviteButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(48.dp)
    ) {
        Icon(
            imageVector = Icons.Default.PersonAdd,
            contentDescription = "Пригласить",
            tint = White
        )
    }
}

@Composable
fun InviteAlertDialog(
    viewModel: UserProfileViewModel,
    onDismiss: () -> Unit,
    onConfirm: (BandItem) -> Unit,
) {
    var selectedBand by remember { mutableStateOf<BandItem?>(null) }
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

                Column(modifier = Modifier.padding(16.dp)) {
                    // Заголовок
                    androidx.compose.material3.Text(
                        text = "Выберите группу",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    LazyColumn {
                        items(viewModel.getMyUserBands()) { band ->
                            var clicked = false
                            BandListItem(band = band, onClicked = { clicked = it }) {
                                if (clicked) {
                                    selectedBand = it
                                }
                            }
                        }
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
                            androidx.compose.material3.Text("Отмена")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = { selectedBand?.let { onConfirm(it) } },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black,
                                contentColor = Color.White
                            ), // Установка черного цвета фона
                        ) {
                            androidx.compose.material3.Text("Отправить")
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun BandListItem(band: BandItem, onClicked: (Boolean) -> Unit, onItemClick: (BandItem) -> Unit) {
    var clicked by remember { mutableStateOf<Boolean>(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                clicked=!clicked
                onClicked(clicked)
                onItemClick(band)
            })
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(contentAlignment = Alignment.Center) {
            AsyncImage(
                model = band.photo.url,
                contentDescription = null,
                modifier = Modifier
                    .size(86.dp)
                    .padding(4.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            if (clicked) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Check",
                    modifier = Modifier
                        .size(86.dp)
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(Black.copy(alpha = .3f)),
                    tint = White
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            androidx.compose.material3.Text(
                text = band.name,
                fontWeight = FontWeight.Bold,
                color = Black
            )
            androidx.compose.material3.Text(
                text = band.genre,
                color = Black
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                band.members.forEach { member ->
                    AsyncImage(
                        model = member.profile_picture.url,
                        contentDescription = null,
                        modifier = Modifier
                            .size(36.dp)
                            .padding(4.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
    HorizontalDivider(
        modifier = Modifier.fillMaxWidth(),
        thickness = 3.dp,
        color = LightBlack
    )
}