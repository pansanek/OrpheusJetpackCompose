package ru.potemkin.orpheusjetpackcompose.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ru.potemkin.orpheusjetpackcompose.domain.entities.AuthItems.AboutMeItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.AuthItems.RegItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.AuthItems.TypeItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorInfoItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
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
    mapScreenContent: @Composable () -> Unit,
    newsFeedScreenContent: @Composable () -> Unit,
    profileScreenContent: @Composable () -> Unit,
    searchScreenContent: @Composable () -> Unit,
    userProfileScreenContent: @Composable (UserItem) -> Unit,
    bandCreationScreenContent: @Composable () -> Unit,
    settingsScreenContent: @Composable (UserItem) -> Unit,
    bandListScreenContent: @Composable () -> Unit,
    changeUserProfileScreenContent: @Composable (UserItem) -> Unit,
    changeBandProfileScreenContent: @Composable (BandItem) -> Unit,
    changeLocationProfileScreenContent: @Composable (LocationItem) -> Unit,
    postCreationScreenContent: @Composable (CreatorInfoItem) -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.FeedHomeScreen.route
    ) {
        feedHomeNavGraph(
            newsFeedScreenContent, commentsScreenContent, userProfileScreenContent, bandProfileScreenContent,bandCreationScreenContent,changeBandProfileScreenContent,postCreationScreenContent
        )
        chatHomeNavGraph(
            chatListScreenContent, chatScreenContent, userProfileScreenContent, bandCreationScreenContent, bandProfileScreenContent,commentsScreenContent, changeBandProfileScreenContent,newsFeedScreenContent
        )
        profileHomeNavGraph(
            profileScreenContent, commentsScreenContent, userProfileScreenContent, bandCreationScreenContent, bandProfileScreenContent, chatListScreenContent, chatScreenContent,searchScreenContent,settingsScreenContent, bandListScreenContent,changeUserProfileScreenContent,changeBandProfileScreenContent,postCreationScreenContent,newsFeedScreenContent
        )
        mapHomeNavGraph(
            userProfileScreenContent, commentsScreenContent, chatListScreenContent, chatScreenContent, mapScreenContent, locationScreenContent,changeLocationProfileScreenContent,postCreationScreenContent,newsFeedScreenContent
        )
    }
}