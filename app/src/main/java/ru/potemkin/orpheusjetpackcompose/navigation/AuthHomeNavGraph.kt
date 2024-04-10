package ru.potemkin.orpheusjetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ru.potemkin.orpheusjetpackcompose.domain.entities.AuthItems.AboutMeItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.AuthItems.RegItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.AuthItems.TypeItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem

fun NavGraphBuilder.authHomeNavGraph(
    startScreenContent: @Composable () -> Unit,
    loginScreenContent: @Composable () -> Unit,
    registrationScreenContent: @Composable () -> Unit,
    registrationAboutMeScreenContent: @Composable (RegItem) -> Unit,
    registrationUserTypeScreenContent: @Composable (AboutMeItem) -> Unit,
    registrationMusicianTypeScreenContent: @Composable (TypeItem) -> Unit,
    registrationAdministratorTypeScreenContent: @Composable (TypeItem) -> Unit,
) {
    navigation(
        startDestination = Screen.StartScreen.route,
        route = Screen.AuthHomeScreen.route
    ) {
        composable(Screen.LoginScreen.route) {
            loginScreenContent()
        }
        composable(Screen.StartScreen.route) {
            startScreenContent()
        }
        composable(Screen.RegistrationScreen.route) {
            registrationScreenContent()
        }
        composable(
            route = Screen.RegistrationAboutMeScreen.route,
            arguments = listOf(
                navArgument(Screen.KEY_REG_ABOUT_ME) {
                    type = RegItem.NavigationType
                }
            )
        ) {
            val args = it.arguments?.getParcelable<RegItem>(Screen.KEY_REG_ABOUT_ME)
                ?: throw RuntimeException("Args is null")
            registrationAboutMeScreenContent(args)
        }
        composable(
            route = Screen.RegistrationUserTypeScreen.route,
            arguments = listOf(
                navArgument(Screen.KEY_REG_TYPE) {
                    type = AboutMeItem.NavigationType
                }
            )
        ) {
            val args = it.arguments?.getParcelable<AboutMeItem>(Screen.KEY_REG_TYPE)
                ?: throw RuntimeException("Args is null")
            registrationUserTypeScreenContent(args)
        }
        composable(
            route = Screen.RegistrationMusicianTypeScreen.route,
            arguments = listOf(
                navArgument(Screen.KEY_REG_MUS) {
                    type = TypeItem.NavigationType
                }
            )
        ) {
            val args = it.arguments?.getParcelable<TypeItem>(Screen.KEY_REG_MUS)
                ?: throw RuntimeException("Args is null")
            registrationMusicianTypeScreenContent(args)
        }
        composable(
            route = Screen.RegistrationAdministratorTypeScreen.route,
            arguments = listOf(
                navArgument(Screen.KEY_REG_ADMN) {
                    type = TypeItem.NavigationType
                }
            )
        ) {
            val args = it.arguments?.getParcelable<TypeItem>(Screen.KEY_REG_ADMN)
                ?: throw RuntimeException("Args is null")
            registrationAdministratorTypeScreenContent(args)
        }
    }
}