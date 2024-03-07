package ru.potemkin.orpheusjetpackcompose.presentation.profile.myprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.filled.Close

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.ui.theme.Black
import ru.potemkin.orpheusjetpackcompose.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackPressed: () -> Unit,
) {
    var isEditProfileScreenVisible by remember { mutableStateOf(false) }
    var isPrivacyScreenVisible by remember { mutableStateOf(false) }
    var userSettings by remember { mutableStateOf(UserSettingsItem(true, false)) }
    Scaffold(
        topBar = {
           TopAppBar(
                title = { Text(
                    text = "Настройки",
                    style = MaterialTheme.typography.titleLarge,
                    color = White
                ) },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            Icons.Default.ArrowBack, contentDescription = "Back",
                            tint = White
                        )
                    }
                },
               backgroundColor = Black
           )
        }
    ) {
        Box(modifier = Modifier.padding(it).background(Black)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                SettingsItem(
                    text = "Редактировать профиль",
                    icon = Icons.Default.Edit,
                    onClick = { isEditProfileScreenVisible = true }
                )

                // Privacy Settings Button
                SettingsItem(
                    text = "Приватность",
                    icon = Icons.Default.Lock,
                    onClick = { isPrivacyScreenVisible = true }
                )


                // Privacy Settings Screen
                if (isPrivacyScreenVisible) {
                    PrivacySettingsScreen(
                        onClose = { isPrivacyScreenVisible = false },
                        userSettings = userSettings,
                        onUserSettingsChanged = { newUserSettings ->
                            userSettings = newUserSettings
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsItem(text: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .padding(end = 8.dp),
            tint = White
        )
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f),
            color = White
        )
    }
}



@Composable
fun PrivacySettingsScreen(
    onClose: () -> Unit,
    userSettings: UserSettingsItem,
    onUserSettingsChanged: (UserSettingsItem) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Настройки приватности",
                style = MaterialTheme.typography.titleLarge,
                color = White
            )
            IconButton(
                onClick = onClose
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = White
                )
            }
        }

        // Switch for receiving messages for new chats
        PrivacySettingItem(
            text = "Можете принимать сообщения для новых чатов",
            isChecked = userSettings.canReceiveMessagesForNewChats,
            onCheckedChange = { isChecked ->
                onUserSettingsChanged(userSettings.copy(canReceiveMessagesForNewChats = isChecked))
            }
        )

        // Switch for receiving band invitations
        PrivacySettingItem(
            text = "Можете принимать приглашения в группы",
            isChecked = userSettings.canReceiveBandInvitations,
            onCheckedChange = { isChecked ->
                onUserSettingsChanged(userSettings.copy(canReceiveBandInvitations = isChecked))
            }
        )
    }
}

@Composable
fun PrivacySettingItem(
    text: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f),
            color = White
        )

        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchColors(
                checkedThumbColor = White,
                checkedTrackColor = Black,
                checkedBorderColor = White,
                checkedIconColor = Black,
                uncheckedThumbColor = White,
                uncheckedTrackColor = Black,
                uncheckedBorderColor = White,
                uncheckedIconColor = Black,
                disabledCheckedThumbColor = Black,
                disabledCheckedTrackColor = Black,
                disabledCheckedBorderColor = Black,
                disabledCheckedIconColor = Black,
                disabledUncheckedThumbColor = Black,
                disabledUncheckedTrackColor = Black,
                disabledUncheckedBorderColor = Black,
                disabledUncheckedIconColor = Black
            ),
        )
    }
}
