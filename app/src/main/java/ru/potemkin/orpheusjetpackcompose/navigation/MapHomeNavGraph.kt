package ru.potemkin.orpheusjetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem

fun NavGraphBuilder.mapHomeNavGraph(
    userProfileScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (PostItem) -> Unit,
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
        composable(Screen.MapScreen.route) {
            mapScreenContent()
        }
        composable(Screen.LocationScreen.route) {
            locationScreenContent()
        }
    }
}