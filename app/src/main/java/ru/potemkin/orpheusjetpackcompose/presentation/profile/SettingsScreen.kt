package ru.potemkin.orpheusjetpackcompose.presentation.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem

@Composable
fun SettingsScreen(
    onBackPressed: () -> Unit,
) {
    var isEditProfileScreenVisible by remember { mutableStateOf(false) }
    var isPrivacyScreenVisible by remember { mutableStateOf(false) }
    var userSettings by remember { mutableStateOf(UserSettingsItem(true, false)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "Настройки",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Edit Profile Button
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

        // Edit Profile Screen
        if (isEditProfileScreenVisible) {
            EditProfileScreen(
                onClose = { isEditProfileScreenVisible = false },
                onSave = { profilePicture, about -> /* Handle profile picture and about text */ }
            )
        }

        // Privacy Settings Screen
        if (isPrivacyScreenVisible) {
            PrivacySettingsScreen(
                onClose = { isPrivacyScreenVisible = false },
                userSettings = userSettings,
                onUserSettingsChanged = { newUserSettings -> userSettings = newUserSettings }
            )
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
                .padding(end = 8.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(onClose: () -> Unit, onSave: (String, String) -> Unit) {
    var profilePicture by remember { mutableStateOf("") }
    var about by remember { mutableStateOf("") }

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
                text = "Редактировать профиль",
                style = MaterialTheme.typography.titleLarge
            )
            IconButton(
                onClick = onClose
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null
                )
            }
        }

        // Profile Picture
        TextField(
            value = profilePicture,
            onValueChange = { profilePicture = it },
            label = { Text("URL изображения профиля") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // About
        TextField(
            value = about,
            onValueChange = { about = it },
            label = { Text("О себе") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Save Button
        Button(
            onClick = { onSave(profilePicture, about) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Сохранить")
        }
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
                style = MaterialTheme.typography.titleLarge
            )
            IconButton(
                onClick = onClose
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null
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
            modifier = Modifier.weight(1f)
        )

        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}

@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    EditProfileScreen(onClose = {}, onSave = { _, _ -> })
}

@Preview(showBackground = true)
@Composable
fun PrivacySettingsScreenPreview() {
    PrivacySettingsScreen(onClose = {}, userSettings = UserSettingsItem(true, false), onUserSettingsChanged = {})
}