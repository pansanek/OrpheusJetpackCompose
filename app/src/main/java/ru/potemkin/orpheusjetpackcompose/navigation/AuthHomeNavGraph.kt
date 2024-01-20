package ru.potemkin.orpheusjetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.authHomeNavGraph(
    startScreenContent: @Composable () -> Unit,
    loginScreenContent: @Composable () -> Unit,
    registrationScreenContent: @Composable () -> Unit,
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
    }
}