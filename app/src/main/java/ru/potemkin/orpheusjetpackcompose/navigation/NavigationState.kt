package ru.potemkin.orpheusjetpackcompose.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.potemkin.orpheusjetpackcompose.domain.entities.AuthItems.AboutMeItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.AuthItems.RegItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.AuthItems.TypeItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem

class NavigationState(
    val navHostController: NavHostController
) {

    fun navigateTo(route: String) {
        navHostController.navigate(route) {
            popUpTo(navHostController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToComments(feedPost: PostItem) {
        navHostController.navigate(Screen.CommentsScreen.getRouteWithArgs(feedPost))
    }

    fun navigateToChat(chatItem: ChatItem) {
        navHostController.navigate(Screen.ChatScreen.getRouteWithArgs(chatItem))
    }
    fun navigateToUser(userItem: UserItem) {
        navHostController.navigate(Screen.UserProfileScreen.getRouteWithArgs(userItem))
    }

    fun navigateToBand(bandItem: BandItem) {
        navHostController.navigate(Screen.BandProfileScreen.getRouteWithArgs(bandItem))
    }
    fun navigateToLocation(locationItem: LocationItem) {
        navHostController.navigate(Screen.LocationScreen.getRouteWithArgs(locationItem))
    }
    fun navigateToRegistration() {
        navHostController.navigate(Screen.RegistrationScreen.route)
    }

    fun navigateToLogin() {
        navHostController.navigate(Screen.LoginScreen.route)
    }
    fun navigateToNewsFeed() {
        navHostController.navigate(Screen.NewsFeedScreen.route)
    }
    fun navigateToSettings() {
        navHostController.navigate(Screen.SettingsScreen.route)
    }
    fun navigateToBandCreation() {
        navHostController.navigate(Screen.BandCreationScreen.route)
    }
    fun navigateToBandList() {
        navHostController.navigate(Screen.BandListScreen.route)
    }
    fun navigateToUserChat(chatItem: ChatItem) {
        Log.d("CHAT","NAVSTATE "+chatItem.toString())
        navHostController.navigate(Screen.ChatScreen.getRouteWithArgs(chatItem))
    }
    fun navigateToAboutMeScreen(regItem: RegItem) {
        navHostController.navigate(Screen.RegistrationAboutMeScreen.getRouteWithArgs(regItem))
    }
    fun navigateToUserTypeScreen(aboutMeItem: AboutMeItem) {
        navHostController.navigate(Screen.RegistrationUserTypeScreen.getRouteWithArgs(aboutMeItem))
    }
    fun navigateToMusicianTypeScreen(typeItem: TypeItem) {
        navHostController.navigate(Screen.RegistrationMusicianTypeScreen.getRouteWithArgs(typeItem))
    }
    fun navigateToAdministratorTypeScreen(typeItem: TypeItem) {
        navHostController.navigate(Screen.RegistrationAdministratorTypeScreen.getRouteWithArgs(typeItem))
    }
}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
): NavigationState {
    return remember {
        NavigationState(navHostController)
    }
}