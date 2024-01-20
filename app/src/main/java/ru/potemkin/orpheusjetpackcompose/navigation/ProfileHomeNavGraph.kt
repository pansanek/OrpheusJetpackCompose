package ru.potemkin.orpheusjetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.profileHomeNavGraph(
    profileScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable () -> Unit,
    userProfileScreenContent: @Composable () -> Unit,
    bandCreationScreenContent: @Composable () -> Unit,
    bandProfileScreenContent: @Composable () -> Unit,
    chatListScreenContent: @Composable () -> Unit,
    chatScreenContent: @Composable () -> Unit,
    searchScreenContent: @Composable () -> Unit,
    settingsScreenContent: @Composable () -> Unit

) {
    navigation(
        startDestination = Screen.ProfileScreen.route,
        route = Screen.ProfileHomeScreen.route
    ) {
        composable(Screen.BandProfileScreen.route) {
            bandProfileScreenContent()
        }
        composable(Screen.BandCreationScreen.route) {
            bandCreationScreenContent()
        }
        composable(Screen.UserProfileScreen.route) {
            userProfileScreenContent()
        }
        composable(Screen.ChatListScreen.route) {
            chatListScreenContent()
        }
        composable(Screen.ChatScreen.route) {
            chatScreenContent()
        }
        composable(Screen.CommentsScreen.route) {
            commentsScreenContent()
        }
        composable(Screen.ProfileScreen.route) {
            profileScreenContent()
        }
        composable(Screen.SettingsScreen.route) {
            settingsScreenContent()
        }
        composable(Screen.SearchScreen.route) {
            searchScreenContent()
        }
    }
}