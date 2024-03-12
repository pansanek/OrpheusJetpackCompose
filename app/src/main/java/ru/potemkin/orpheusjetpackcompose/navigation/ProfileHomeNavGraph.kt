package ru.potemkin.orpheusjetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorInfoItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
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
    settingsScreenContent: @Composable (UserItem) -> Unit,
    bandListScreenContent: @Composable () -> Unit,
    changeUserProfileScreenContent: @Composable (UserItem) -> Unit,
    changeBandProfileScreenContent: @Composable (BandItem) -> Unit,
    postCreationScreenContent: @Composable (CreatorInfoItem) -> Unit,
    newsFeedScreenContent: @Composable () -> Unit,
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
        composable(route = Screen.SettingsScreen.route,
            arguments = listOf(
                navArgument(Screen.KEY_SETTINGS) {
                    type = UserItem.NavigationType
                }
            )
        ) {
            val user = it.arguments?.getParcelable<UserItem>(Screen.KEY_SETTINGS)
                ?: throw RuntimeException("Args is null")
            settingsScreenContent(user)
        }
        composable(Screen.SearchScreen.route) {
            searchScreenContent()
        }
        composable(route = Screen.ChangeUserProfileScreen.route,
            arguments =
            listOf(
                navArgument(Screen.KEY_CHANGE_USER_PROFILE) {
                    type = UserItem.NavigationType
                }
            )){
            val user = it.arguments?.getParcelable<UserItem>(Screen.KEY_CHANGE_USER_PROFILE)
                ?: throw RuntimeException("Args is null")
            changeUserProfileScreenContent(user)
        }
        composable(route = Screen.ChangeBandProfileScreen.route,
            arguments =
            listOf(
                navArgument(Screen.KEY_CHANGE_BAND_PROFILE) {
                    type = BandItem.NavigationType
                }
            )){
            val band = it.arguments?.getParcelable<BandItem>(Screen.KEY_CHANGE_BAND_PROFILE)
                ?: throw RuntimeException("Args is null")
            changeBandProfileScreenContent(band)
        }
        composable(Screen.NewsFeedScreen.route) {
            newsFeedScreenContent()
        }
    }
}