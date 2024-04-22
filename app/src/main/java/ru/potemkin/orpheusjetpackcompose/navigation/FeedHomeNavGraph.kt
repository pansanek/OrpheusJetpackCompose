package ru.potemkin.orpheusjetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorInfoItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.presentation.post.PostCreationScreen

fun NavGraphBuilder.feedHomeNavGraph(
    newsFeedScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (PostItem) -> Unit,
    userProfileScreenContent: @Composable (UserItem) -> Unit,
    postCreationScreenContent: @Composable (CreatorInfoItem) -> Unit
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
        composable(
            route = Screen.UserProfileScreen.route,
            arguments = listOf(
                navArgument(Screen.KEY_USER) {
                    type = UserItem.NavigationType
                }
            )
        ) {
            val user = it.arguments?.getParcelable<UserItem>(Screen.KEY_USER)
                ?: throw RuntimeException("Args is null")
            userProfileScreenContent(user)
        }
        composable(
            route = Screen.PostCreationScreen.route,
            arguments = listOf(
                navArgument(Screen.KEY_CREATE_POST) {
                    type = CreatorInfoItem.NavigationType
                }
            )
        ) {
            val creatorInfoItem = it.arguments?.getParcelable<CreatorInfoItem>(Screen.KEY_CREATE_POST)
                ?: throw RuntimeException("Args is null")
            postCreationScreenContent(creatorInfoItem)
        }
        composable(Screen.NewsFeedScreen.route) {
            newsFeedScreenContent()
        }
    }
}