package ru.potemkin.orpheusjetpackcompose.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import ru.potemkin.orpheusjetpackcompose.navigation.Screen

sealed class NavigationItem(
    val screen: Screen,
    val icon: ImageVector
) {

    object NewsFeed : NavigationItem(
        screen = Screen.FeedHomeScreen,
        icon = Icons.Default.Newspaper
    )

    object Chat : NavigationItem(
        screen = Screen.ChatHomeScreen,
        icon =  Icons.Default.ChatBubble
    )

    object Map : NavigationItem(
        screen = Screen.MapHomeScreen,
        icon =  Icons.Default.Map
    )

    object Profile : NavigationItem(
        screen = Screen.ProfileHomeScreen,
        icon = Icons.Default.Person
    )
}