package ru.potemkin.orpheusjetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.feedHomeNavGraph(
    newsFeedScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable () -> Unit,
    userProfileScreenContent: @Composable () -> Unit,
    notificationsScreenContent: @Composable () -> Unit,
    bandProfileScreenContent: @Composable () -> Unit,
    bandCreationScreenContent: @Composable () -> Unit,
) {
    navigation(
        startDestination = Screen.NewsFeedScreen.route,
        route = Screen.FeedHomeScreen.route
    ) {
        composable(Screen.NewsFeedScreen.route) {
            newsFeedScreenContent()
        }
        composable(Screen.CommentsScreen.route) {
            commentsScreenContent()
        }
        composable(Screen.UserProfileScreen.route) {
            userProfileScreenContent()
        }
        composable(Screen.NotificationsScreen.route) {
            notificationsScreenContent()
        }
        composable(Screen.BandProfileScreen.route) {
            bandProfileScreenContent()
        }
        composable(Screen.BandCreationScreen.route) {
            bandCreationScreenContent()
        }
    }
}