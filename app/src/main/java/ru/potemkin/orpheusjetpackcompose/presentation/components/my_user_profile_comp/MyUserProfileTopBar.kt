package ru.potemkin.orpheusjetpackcompose.presentation.components.my_user_profile_comp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.potemkin.orpheusjetpackcompose.ui.theme.White

@Composable
fun MyUserProfileTopBar(onSettingsClickListener: () -> Unit,) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopAppBar(
            title = { Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "pansanek", color = White)
                Spacer(modifier = Modifier.weight(1f)) // Занимаем всю доступную ширину
                SettingsButton({ onSettingsClickListener() })
            }
            },

            backgroundColor = androidx.compose.ui.graphics.Color.Black
        )
        // Добавьте функциональность для кнопки "Пригласить"

    }
}



@Composable
fun SettingsButton(onSettingsClickListener: () -> Unit,) {
    IconButton(
        onClick = { onSettingsClickListener() },
        modifier = Modifier.size(48.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Настройки",
            tint = White
        )
    }
}