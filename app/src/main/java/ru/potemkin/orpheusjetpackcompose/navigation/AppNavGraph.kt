package ru.potemkin.orpheusjetpackcompose.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ru.potemkin.orpheusjetpackcompose.domain.entities.AuthItems.AboutMeItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.AuthItems.RegItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.AuthItems.TypeItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    bandProfileScreenContent: @Composable (BandItem) -> Unit,
    chatListScreenContent: @Composable () -> Unit,
    chatScreenContent: @Composable (ChatItem) -> Unit,
    commentsScreenContent: @Composable (PostItem) -> Unit,
    locationScreenContent: @Composable (LocationItem) -> Unit,
    loginScreenContent: @Composable () -> Unit,
    mapScreenContent: @Composable () -> Unit,
    newsFeedScreenContent: @Composable () -> Unit,
    profileScreenContent: @Composable () -> Unit,
    registrationScreenContent: @Composable () -> Unit,
    registrationAboutMeScreenContent: @Composable (RegItem) -> Unit,
    registrationUserTypeScreenContent: @Composable (AboutMeItem) -> Unit,
    registrationMusicianTypeScreenContent: @Composable (TypeItem) -> Unit,
    registrationAdministratorTypeScreenContent: @Composable (TypeItem) -> Unit,
    searchScreenContent: @Composable () -> Unit,
    startScreenContent: @Composable () -> Unit,
    userProfileScreenContent: @Composable (UserItem) -> Unit,
    bandCreationScreenContent: @Composable () -> Unit,
    settingsScreenContent: @Composable () -> Unit,
    bandListScreenContent: @Composable () -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.FeedHomeScreen.route
    ) {
        authHomeNavGraph(
            startScreenContent, loginScreenContent, registrationScreenContent,registrationAboutMeScreenContent, registrationUserTypeScreenContent, registrationMusicianTypeScreenContent, registrationAdministratorTypeScreenContent
        )
        feedHomeNavGraph(
            newsFeedScreenContent, commentsScreenContent, userProfileScreenContent, bandProfileScreenContent,bandCreationScreenContent
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