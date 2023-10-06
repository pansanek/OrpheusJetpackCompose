package ru.potemkin.orpheusjetpackcompose.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.potemkin.orpheusjetpackcompose.data.BottomNavItem
import ru.potemkin.orpheusjetpackcompose.presentation.navigation.CHAT_SCREEN
import ru.potemkin.orpheusjetpackcompose.presentation.navigation.MAP_SCREEN
import ru.potemkin.orpheusjetpackcompose.presentation.navigation.NEWS_SCREEN
import ru.potemkin.orpheusjetpackcompose.presentation.navigation.PROFILE_SCREEN

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(navHostController: NavHostController) {

    val items = listOf(
        BottomNavItem("Новости", Icons.Default.Newspaper, NEWS_SCREEN),
        BottomNavItem("Карта", Icons.Default.Map, MAP_SCREEN),
        BottomNavItem("Чаты", Icons.Default.ChatBubble, CHAT_SCREEN),
        BottomNavItem("Профиль", Icons.Default.Person, PROFILE_SCREEN)
    )

    NavigationBar(
        Modifier.background(Color.White)
    ) {
        val navBackStackEntry by navHostController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            NavigationDrawerItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                label = { Text(text = item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    Log.d("NAV", "Clicked ${item.route}")
                    navHostController.navigate(item.route)
                }
            )
        }
    }

}