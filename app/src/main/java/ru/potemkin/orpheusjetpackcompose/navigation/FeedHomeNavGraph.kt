package ru.potemkin.orpheusjetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem

fun NavGraphBuilder.feedHomeNavGraph(
    newsFeedScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (PostItem) -> Unit,
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