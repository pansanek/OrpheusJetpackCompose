package ru.potemkin.orpheusjetpackcompose.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.potemkin.orpheusjetpackcompose.presentation.screens.AboutMeScreen
import ru.potemkin.orpheusjetpackcompose.presentation.screens.AdminRegScreen
import ru.potemkin.orpheusjetpackcompose.presentation.screens.ChatListScreen
import ru.potemkin.orpheusjetpackcompose.presentation.screens.ChatScreen
import ru.potemkin.orpheusjetpackcompose.presentation.screens.LocationScreen
import ru.potemkin.orpheusjetpackcompose.presentation.screens.LoginScreen
import ru.potemkin.orpheusjetpackcompose.presentation.screens.MapScreen
import ru.potemkin.orpheusjetpackcompose.presentation.screens.MusicianRegScreen
import ru.potemkin.orpheusjetpackcompose.presentation.screens.NewsFeedScreen
import ru.potemkin.orpheusjetpackcompose.presentation.screens.ProfileScreen
import ru.potemkin.orpheusjetpackcompose.presentation.screens.RegistrationScreen
import ru.potemkin.orpheusjetpackcompose.presentation.screens.StartScreen
import ru.potemkin.orpheusjetpackcompose.presentation.screens.UserProfileScreen
import ru.potemkin.orpheusjetpackcompose.presentation.screens.UserTypeScreen
import ru.potemkin.orpheusjetpackcompose.presentation.viewmodels.ChatListViewModel
import ru.potemkin.orpheusjetpackcompose.presentation.viewmodels.ChatViewModel
import ru.potemkin.orpheusjetpackcompose.presentation.viewmodels.MapViewModel
import ru.potemkin.orpheusjetpackcompose.presentation.viewmodels.NewsViewModel

@Composable
fun MainNavigation() {

    val navHostController = rememberNavController()
    val application = Application()
    val chatViewModel = ChatViewModel(application)
    val chatListViewModel = ChatListViewModel(application)
    val mapViewModel = MapViewModel(application)
    val newsViewModel = NewsViewModel(application)

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
            NewsFeedScreen(navHostController,newsViewModel)
        }
        composable(PROFILE_SCREEN) {
            ProfileScreen(navHostController,newsViewModel)
        }
        composable(USER_PROFILE_SCREEN) {
            UserProfileScreen(navHostController,newsViewModel)
        }
        composable(MAP_SCREEN) {
            MapScreen(navHostController,mapViewModel)
        }
        composable(LOCATION_SCREEN) {
            LocationScreen(navHostController)
        }
        composable(REG_SCREEN) {
            RegistrationScreen(navHostController)
        }
        composable(ABOUT_ME_SCREEN) {
            AboutMeScreen(navHostController)
        }
        composable(USER_TYPE_SCREEN) {
            UserTypeScreen(navHostController)
        }
        composable(MUS_REG_SCREEN) {
            MusicianRegScreen(navHostController)
        }
        composable(ADM_REG_SCREEN) {
            AdminRegScreen(navHostController)
        }
    }

}

const val START_SCREEN = "Start screen"
const val CHAT_LIST_SCREEN = "Chat List screen"
const val CHAT_SCREEN = "Chat screen"
const val NEWS_SCREEN = "News screen"
const val MAP_SCREEN = "Map screen"
const val PROFILE_SCREEN = "Profile screen"
const val USER_PROFILE_SCREEN = "User profile screen"
const val LOCATION_SCREEN = "Location screen"
const val BAND_PROFILE_SCREEN = "Band profile screen"
const val SEARCH_SCREEN = "Search screen"

const val LOG_SCREEN = "Login screen"
const val REG_SCREEN = "Registration screen"
const val ABOUT_ME_SCREEN = "About me screen"
const val USER_TYPE_SCREEN = "User type screen"
const val MUS_REG_SCREEN = "Musician registration screen"
const val ADM_REG_SCREEN = "Venue administrator registration screen"
