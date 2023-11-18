package ru.potemkin.orpheusjetpackcompose.presentation.components.band_profile_comp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.potemkin.orpheusjetpackcompose.ui.theme.White

@Composable
fun BandProfileTopBar(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = { Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "The Silence Still Echoes", color = White)
                Spacer(modifier = Modifier.weight(1f)) // Занимаем всю доступную ширину
            }
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Назад"
                    , tint = White
                    )
                }
            },
            backgroundColor = androidx.compose.ui.graphics.Color.Black
        )
        // Добавьте функциональность для кнопки "Пригласить"

    }
}



