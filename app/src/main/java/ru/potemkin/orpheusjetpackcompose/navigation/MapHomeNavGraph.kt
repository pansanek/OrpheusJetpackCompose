package ru.potemkin.orpheusjetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem

fun NavGraphBuilder.mapHomeNavGraph(
    userProfileScreenContent: @Composable (UserItem) -> Unit,
    commentsScreenContent: @Composable (PostItem) -> Unit,
    chatListScreenContent: @Composable () -> Unit,
    chatScreenContent: @Composable (ChatItem) -> Unit,
    mapScreenContent: @Composable () -> Unit,
    locationScreenContent: @Composable (LocationItem) -> Unit,
    changeLocationProfileScreenContent: @Composable (LocationItem) -> Unit
) {
    navigation(
        startDestination = Screen.MapScreen.route,
        route = Screen.MapHomeScreen.route
    ) {
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
        composable(Screen.MapScreen.route) {
            mapScreenContent()
        }
        composable(
            route = Screen.LocationScreen.route,
            arguments = listOf(
                navArgument(Screen.KEY_LOCATION) {
                    type = LocationItem.NavigationType
                }
            )
        ) {
            val location = it.arguments?.getParcelable<LocationItem>(Screen.KEY_LOCATION)
                ?: throw RuntimeException("Args is null")
            locationScreenContent(location)
        }
        composable(route = Screen.ChangeLocationProfileScreen.route,
            arguments =
            listOf(
                navArgument(Screen.KEY_CHANGE_LOCATION_PROFILE) {
                    type = LocationItem.NavigationType
                }
            )){
            val location = it.arguments?.getParcelable<LocationItem>(Screen.KEY_CHANGE_LOCATION_PROFILE)
                ?: throw RuntimeException("Args is null")
            changeLocationProfileScreenContent(location)
        }
    }
}