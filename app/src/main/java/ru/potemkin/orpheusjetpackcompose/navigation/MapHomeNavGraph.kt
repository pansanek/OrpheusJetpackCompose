package ru.potemkin.orpheusjetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorInfoItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem

fun NavGraphBuilder.mapHomeNavGraph(
    mapScreenContent: @Composable () -> Unit,
    locationScreenContent: @Composable (LocationItem) -> Unit,
    changeLocationProfileScreenContent: @Composable (LocationItem) -> Unit,
    newsFeedScreenContent: @Composable () -> Unit,

) {
    navigation(
        startDestination = Screen.MapScreen.route,
        route = Screen.MapHomeScreen.route
    ) {



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

        composable(Screen.NewsFeedScreen.route) {
            newsFeedScreenContent()
        }

    }
}