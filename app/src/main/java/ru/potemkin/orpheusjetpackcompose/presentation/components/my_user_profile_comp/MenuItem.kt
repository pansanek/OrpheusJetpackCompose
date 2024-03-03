package ru.potemkin.orpheusjetpackcompose.presentation.components.my_user_profile_comp

import androidx.compose.ui.graphics.vector.ImageVector


data class MenuItem(
    val id: String,
    val title: String,
    val contentDescription: String,
    val icon: ImageVector
)