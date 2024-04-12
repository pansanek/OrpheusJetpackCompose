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
fun AuthNavGraph(
    navHostController: NavHostController,
    mainScreenContent: @Composable () -> Unit,
    loginScreenContent: @Composable () -> Unit,
    registrationScreenContent: @Composable () -> Unit,
    registrationAboutMeScreenContent: @Composable (RegItem) -> Unit,
    registrationUserTypeScreenContent: @Composable (AboutMeItem) -> Unit,
    registrationMusicianTypeScreenContent: @Composable (TypeItem) -> Unit,
    registrationAdministratorTypeScreenContent: @Composable (TypeItem) -> Unit,
    startScreenContent: @Composable () -> Unit,

    ) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.AuthHomeScreen.route
    ) {
        authHomeNavGraph(
            mainScreenContent,startScreenContent, loginScreenContent, registrationScreenContent,registrationAboutMeScreenContent, registrationUserTypeScreenContent, registrationMusicianTypeScreenContent, registrationAdministratorTypeScreenContent
        )

    }
}