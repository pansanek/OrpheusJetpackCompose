package ru.potemkin.orpheusjetpackcompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.potemkin.orpheusjetpackcompose.presentation.screens.ChatListScreen
import ru.potemkin.orpheusjetpackcompose.presentation.screens.ChatScreen
import ru.potemkin.orpheusjetpackcompose.presentation.screens.LocationScreen
import ru.potemkin.orpheusjetpackcompose.presentation.screens.LoginScreen
import ru.potemkin.orpheusjetpackcompose.presentation.screens.MapScreen
import ru.potemkin.orpheusjetpackcompose.presentation.screens.NewsFeedScreen
import ru.potemkin.orpheusjetpackcompose.presentation.screens.ProfileScreen
import ru.potemkin.orpheusjetpackcompose.presentation.screens.RegistrationScreen
import ru.potemkin.orpheusjetpackcompose.presentation.screens.StartScreen
import ru.potemkin.orpheusjetpackcompose.presentation.screens.UserProfileScreen
import ru.potemkin.orpheusjetpackcompose.presentation.viewmodels.ChatListViewModel
import ru.potemkin.orpheusjetpackcompose.presentation.viewmodels.ChatViewModel

@Composable
fun MainNavigation() {

    val navHostController = rememberNavController()
    val chatViewModel = ChatViewModel()
    val chatListViewModel = ChatListViewModel()

    NavHost(navController = navHostController, startDestination = START_SCREEN) {
        composable(START_SCREEN) {
            StartScreen(navHostController)
        }
        composable(CHAT_LIST_SCREEN) {
            ChatListScreen(navHostController,chatListViewModel)
        }
        composable(CHAT_SCREEN) {
            ChatScreen(navHostController,chatViewModel)
        }
        composable(LOG_SCREEN) {
            LoginScreen(navHostController)
        }
        composable(REG_SCREEN) {
            RegistrationScreen(navHostController)
        }
        composable(NEWS_SCREEN) {
            NewsFeedScreen(navHostController)
        }
        composable(PROFILE_SCREEN) {
            ProfileScreen(navHostController)
        }
        composable(USER_PROFILE_SCREEN) {
            UserProfileScreen(navHostController)
        }
        composable(MAP_SCREEN) {
            MapScreen(navHostController)
        }
        composable(LOCATION_SCREEN) {
            LocationScreen(navHostController)
        }
    }

}

const val START_SCREEN = "Start screen"
const val CHAT_LIST_SCREEN = "Chat List screen"
const val CHAT_SCREEN = "Chat screen"
const val LOG_SCREEN = "Login screen"
const val REG_SCREEN = "Registration screen"
const val NEWS_SCREEN = "News screen"
const val MAP_SCREEN = "Map screen"
const val PROFILE_SCREEN = "Profile screen"
const val USER_PROFILE_SCREEN = "User profile screen"
const val LOCATION_SCREEN = "Location screen"