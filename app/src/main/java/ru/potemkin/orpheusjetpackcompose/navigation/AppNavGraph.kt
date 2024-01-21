package ru.potemkin.orpheusjetpackcompose.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    bandProfileScreenContent: @Composable () -> Unit,
    chatListScreenContent: @Composable () -> Unit,
    chatScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (PostItem) -> Unit,
    locationScreenContent: @Composable () -> Unit,
    loginScreenContent: @Composable () -> Unit,
    mapScreenContent: @Composable () -> Unit,
    newsFeedScreenContent: @Composable () -> Unit,
    profileScreenContent: @Composable () -> Unit,
    registrationScreenContent: @Composable () -> Unit,
    searchScreenContent: @Composable () -> Unit,
    startScreenContent: @Composable () -> Unit,
    userProfileScreenContent: @Composable () -> Unit,
    bandCreationScreenContent: @Composable () -> Unit,
    notificationsScreenContent: @Composable () -> Unit,
    settingsScreenContent: @Composable () -> Unit,
    bandListScreenContent: @Composable () -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.StartScreen.route
    ) {
        authHomeNavGraph(
            startScreenContent, loginScreenContent, registrationScreenContent
        )
        feedHomeNavGraph(
            newsFeedScreenContent, commentsScreenContent, userProfileScreenContent, notificationsScreenContent, bandProfileScreenContent,bandCreationScreenContent
        )
        chatHomeNavGraph(
            chatListScreenContent, chatScreenContent, userProfileScreenContent, bandCreationScreenContent, bandProfileScreenContent,commentsScreenContent,
        )
        profileHomeNavGraph(
            profileScreenContent, commentsScreenContent, userProfileScreenContent, bandCreationScreenContent, bandProfileScreenContent, chatListScreenContent, chatScreenContent,settingsScreenContent,searchScreenContent, bandListScreenContent
        )
        mapHomeNavGraph(
            userProfileScreenContent, commentsScreenContent, chatListScreenContent, chatScreenContent, mapScreenContent, locationScreenContent
        )
    }
}