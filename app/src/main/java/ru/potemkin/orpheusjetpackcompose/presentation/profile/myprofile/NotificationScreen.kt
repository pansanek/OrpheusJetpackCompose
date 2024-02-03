package ru.potemkin.orpheusjetpackcompose.presentation.profile.myprofile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class NotificationItem(val title: String, val content: String, val icon: ImageVector)

@Composable
fun NotificationCard(notification: NotificationItem) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = notification.icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 8.dp)
                )
                Text(
                    text = notification.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = notification.content,
                style = MaterialTheme.typography.titleSmall,
            )
        }
    }
}

@Composable
fun NotificationScreen(
    onBackPressed: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        var notificationData = listOf(
            NotificationItem(
            title = "Notification Title",
            content = "This is the notification content. You can provide additional information here.",
            icon = Icons.Default.Notifications), NotificationItem(
            title = "Notification Title",
            content = "This is the notification content. You can provide additional information here.",
            icon = Icons.Default.Notifications), NotificationItem(
            title = "Notification Title",
            content = "This is the notification content. You can provide additional information here.",
            icon = Icons.Default.Notifications)
        )
        items(notificationData) { notification ->
            NotificationCard(notification = notification)
        }
    }
}

@Composable
fun NotificationIcon(modifier: Modifier = Modifier) {
    Icon(
        imageVector = Icons.Default.Notifications,
        contentDescription = null,
        tint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
        modifier = modifier
    )
}

//@Composable
//@Preview(showBackground = true)
//fun NotificationScreenPreview() {
//    OrpheusJetpackComposeTheme {
//        NotificationScreen()
//    }
//}

@Preview(showBackground = true)
@Composable
fun NotificationCardPreview() {
    NotificationCard(
        notification = NotificationItem(
            title = "Notification Title",
            content = "This is the notification content. You can provide additional information here.",
            icon = Icons.Default.Notifications
        )
    )
}