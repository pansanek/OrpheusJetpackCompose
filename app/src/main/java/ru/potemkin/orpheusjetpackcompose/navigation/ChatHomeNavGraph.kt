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

fun NavGraphBuilder.chatHomeNavGraph(
    chatListScreenContent: @Composable () -> Unit,
    chatScreenContent: @Composable (ChatItem) -> Unit,
    newsFeedScreenContent: @Composable () -> Unit,
) {
    navigation(
        startDestination = Screen.ChatListScreen.route,
        route = Screen.ChatHomeScreen.route
    ) {
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

        composable(Screen.NewsFeedScreen.route) {
            newsFeedScreenContent()
        }
    }
}