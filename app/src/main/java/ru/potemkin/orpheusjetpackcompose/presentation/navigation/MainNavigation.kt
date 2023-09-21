package ru.potemkin.orpheusjetpackcompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.potemkin.orpheusjetpackcompose.presentation.screens.ChatListScreen
import ru.potemkin.orpheusjetpackcompose.presentation.screens.ChatScreen
import ru.potemkin.orpheusjetpackcompose.presentation.screens.LoginScreen
import ru.potemkin.orpheusjetpackcompose.presentation.screens.RegistrationScreen
import ru.potemkin.orpheusjetpackcompose.presentation.screens.StartScreen

@Composable
fun MainNavigation() {

    val navHostController = rememberNavController()

    NavHost(navController = navHostController, startDestination = START_SCREEN) {
        composable(START_SCREEN) {
            StartScreen(navHostController)
        }
        composable(CHAT_LIST_SCREEN) {
            ChatListScreen(navHostController)
        }
        composable(CHAT_SCREEN) {
            ChatScreen(navHostController)
        }
        composable(LOG_SCREEN) {
            LoginScreen(navHostController)
        }
        composable(REG_SCREEN) {
            RegistrationScreen(navHostController)
        }
    }

}

const val START_SCREEN = "Start screen"
const val CHAT_LIST_SCREEN = "Chat List screen"
const val CHAT_SCREEN = "Chat screen"
const val LOG_SCREEN = "Login screen"
const val REG_SCREEN = "Registration screen"