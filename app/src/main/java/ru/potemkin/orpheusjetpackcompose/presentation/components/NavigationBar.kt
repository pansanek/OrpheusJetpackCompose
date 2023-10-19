package ru.potemkin.orpheusjetpackcompose.presentation.components

import android.util.Log
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.potemkin.orpheusjetpackcompose.data.BottomNavItem
import ru.potemkin.orpheusjetpackcompose.presentation.navigation.CHAT_LIST_SCREEN
import ru.potemkin.orpheusjetpackcompose.presentation.navigation.CHAT_SCREEN
import ru.potemkin.orpheusjetpackcompose.presentation.navigation.MAP_SCREEN
import ru.potemkin.orpheusjetpackcompose.presentation.navigation.NEWS_SCREEN
import ru.potemkin.orpheusjetpackcompose.presentation.navigation.PROFILE_SCREEN

@Composable
fun NavigationBar(navHostController: NavHostController) {

    val items = listOf(
        BottomNavItem("Новости", Icons.Default.Newspaper, NEWS_SCREEN),
        BottomNavItem("Карта", Icons.Default.Map, MAP_SCREEN),
        BottomNavItem("Чаты", Icons.Default.ChatBubble, CHAT_LIST_SCREEN),
        BottomNavItem("Профиль", Icons.Default.Person, PROFILE_SCREEN)
    )

    BottomNavigation(
        backgroundColor = Color.White
    ) {
        val navBackStackEntry by navHostController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    Log.d("NAV", "Clicked ${item.route}")
                    navHostController.navigate(item.route)
                }
            )
        }
    }

}