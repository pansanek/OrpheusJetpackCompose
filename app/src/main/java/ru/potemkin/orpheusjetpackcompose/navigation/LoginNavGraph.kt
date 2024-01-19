package ru.potemkin.orpheusjetpackcompose.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.loginNavGraph(
    newsFeedScreenContent: @Composable () -> Unit,
    registrationScreenContent:@Composable () -> Unit,
) {
    navigation(
        startDestination = Screen.NewsFeedScreen.route,
        route = Screen.LoginScreen.route
    ){
        composable(Screen.NewsFeedScreen.route) {
            newsFeedScreenContent()
        }
        composable(Screen.RegistrationScreen.route) {
            registrationScreenContent()
        }
    }
}