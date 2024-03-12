package ru.potemkin.orpheusjetpackcompose.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorInfoItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.NotificationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.NotificationType
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.presentation.components.my_user_profile_comp.MenuItem
import ru.potemkin.orpheusjetpackcompose.ui.theme.Black
import ru.potemkin.orpheusjetpackcompose.ui.theme.Grey
import ru.potemkin.orpheusjetpackcompose.ui.theme.LightBlack
import ru.potemkin.orpheusjetpackcompose.ui.theme.White

@Composable
fun NewsFeedTopBar(
    onPostCreateClickListener: (CreatorInfoItem) -> Unit,
    onDrawerClickListener: () -> Unit
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
                        PostCreationButton (onPostCreateClickListener)
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
) {
    IconButton(
        onClick = {
            onPostCreateClickListener((
                    CreatorInfoItem(
                        "1", "pansanek",
                        PhotoUrlItem(
                            "b59ae42e-8859-441a-9a3a-2fca1b784de3",
                            "https://images6.fanpop.com/image/photos/38800000/-Matt-Nicholls-Upset-Magazine-Portrait-bring-me-the-horizon-38883120-1500-2250.jpg"
                        ),
                        "MUSICIAN"
                    )
                    ))
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
        Text(text = "Уведомления", fontSize = 24.sp, color = White,fontWeight = FontWeight.Bold)
    }
}

@Composable
fun NewsFeedDrawerBody(
    items: List<NotificationItem>,
    modifier: Modifier = Modifier,
    onUserClickListener: (UserItem) -> Unit,
    onBandClickListener: (BandItem) -> Unit,
) {
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
                            if(
                                item.type == NotificationType.LIKE
                                ) item.fromUser.login
                                    +item.contentDescription
                                    + (item.postItem?.date ?:" ")
                            else item.fromUser.login
                                    +item.contentDescription
                            + (item.bandItem?.name ?: ""),
                            color = White,
                            modifier = Modifier.clickable {
                                if(item.bandItem!=null){
                                    onBandClickListener(item.bandItem!!)
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