package ru.potemkin.orpheusjetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem

fun NavGraphBuilder.profileHomeNavGraph(
    profileScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (PostItem) -> Unit,
    userProfileScreenContent: @Composable (UserItem) -> Unit,
    bandCreationScreenContent: @Composable () -> Unit,
    bandProfileScreenContent: @Composable (BandItem) -> Unit,
    chatListScreenContent: @Composable () -> Unit,
    chatScreenContent: @Composable (ChatItem) -> Unit,
    searchScreenContent: @Composable () -> Unit,
    settingsScreenContent: @Composable () -> Unit,
    bandListScreenContent: @Composable () -> Unit,

    ) {
    navigation(
        startDestination = Screen.ProfileScreen.route,
        route = Screen.ProfileHomeScreen.route
    ) {
        composable(
            route = Screen.BandProfileScreen.route,
            arguments = listOf(
                navArgument(Screen.KEY_BAND) {
                    type = BandItem.NavigationType
                }
            )
        ) {
            val band = it.arguments?.getParcelable<BandItem>(Screen.KEY_BAND)
                ?: throw RuntimeException("Args is null")
            bandProfileScreenContent(band)
        }
        composable(Screen.BandCreationScreen.route) {
            bandCreationScreenContent()
        }
        composable(Screen.BandListScreen.route) {
            bandListScreenContent()
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
        composable(Screen.ChatListScreen.route) {
            chatListScreenContent()
        }
        composable(
            route = Screen.ChatScreen.route,
            arguments = listOf(
                navArgument(Screen.KEY_CHAT) {
                    type = ChatItem.NavigationType
                }
            )
        ) {
            val chat = it.arguments?.getParcelable<ChatItem>(Screen.KEY_CHAT)
                ?: throw RuntimeException("Args is null")
            chatScreenContent(chat)
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