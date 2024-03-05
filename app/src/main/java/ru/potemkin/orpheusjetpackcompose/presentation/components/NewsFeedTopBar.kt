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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.presentation.components.my_user_profile_comp.MenuItem
import ru.potemkin.orpheusjetpackcompose.ui.theme.Black
import ru.potemkin.orpheusjetpackcompose.ui.theme.White

@Composable
fun NewsFeedTopBar(
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
                    ){
                        PostCreationButton()
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
fun PostCreationButton() {
    IconButton(
        onClick = {  },
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
        Text(text = "Ваши уведомления", fontSize = 32.sp)
    }
}

@Composable
fun NewsFeedDrawerBody(
    items: List<MenuItem>,
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp),
    onItemClick: (MenuItem) -> Unit
) {
    LazyColumn(modifier) {
        items(items) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemClick(item)
                    }
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.contentDescription,
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = item.title,
                    style = itemTextStyle,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}