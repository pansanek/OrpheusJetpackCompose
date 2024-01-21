package ru.potemkin.orpheusjetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem

fun NavGraphBuilder.profileHomeNavGraph(
    profileScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (PostItem) -> Unit,
    userProfileScreenContent: @Composable () -> Unit,
    bandCreationScreenContent: @Composable () -> Unit,
    bandProfileScreenContent: @Composable () -> Unit,
    chatListScreenContent: @Composable () -> Unit,
    chatScreenContent: @Composable () -> Unit,
    searchScreenContent: @Composable () -> Unit,
    settingsScreenContent: @Composable () -> Unit,
    bandListScreenContent: @Composable () -> Unit,

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
        composable(Screen.BandListScreen.route) {
            bandListScreenContent()
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
        composable(
            route = Screen.CommentsScreen.route,
            arguments = listOf(
                navArgument(Screen.KEY_FEED_POST) {
                    type = PostItem.NavigationType
                }
            )
        ) { //comments/{feed_post_id}
            val feedPost = it.arguments?.getParcelable<PostItem>(Screen.KEY_FEED_POST)
                ?: throw RuntimeException("Args is null")
            commentsScreenContent(feedPost)
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