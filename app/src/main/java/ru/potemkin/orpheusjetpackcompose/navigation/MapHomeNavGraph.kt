package ru.potemkin.orpheusjetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.mapHomeNavGraph(
    userProfileScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable () -> Unit,
    chatListScreenContent: @Composable () -> Unit,
    chatScreenContent: @Composable () -> Unit,
    mapScreenContent: @Composable () -> Unit,
    locationScreenContent: @Composable () -> Unit,
) {
    navigation(
        startDestination = Screen.MapScreen.route,
        route = Screen.MapHomeScreen.route
    ) {
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
        composable(Screen.MapScreen.route) {
            mapScreenContent()
        }
        composable(Screen.LocationScreen.route) {
            locationScreenContent()
        }
    }
}