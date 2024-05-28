package ru.potemkin.orpheusjetpackcompose.presentation.main

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.potemkin.orpheusjetpackcompose.data.preferences.AuthPreferences
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.navigation.AuthNavGraph
import ru.potemkin.orpheusjetpackcompose.navigation.rememberNavigationState
import ru.potemkin.orpheusjetpackcompose.presentation.auth.AboutMeScreen
import ru.potemkin.orpheusjetpackcompose.presentation.auth.AdminRegScreen
import ru.potemkin.orpheusjetpackcompose.presentation.auth.AuthState
import ru.potemkin.orpheusjetpackcompose.presentation.auth.AuthViewModel
import ru.potemkin.orpheusjetpackcompose.presentation.auth.LoginScreen
import ru.potemkin.orpheusjetpackcompose.presentation.auth.MusicianRegScreen
import ru.potemkin.orpheusjetpackcompose.presentation.auth.RegistrationScreen
import ru.potemkin.orpheusjetpackcompose.presentation.auth.StartScreen
import ru.potemkin.orpheusjetpackcompose.presentation.auth.UserTypeScreen
import ru.potemkin.orpheusjetpackcompose.presentation.profile.otherusers.UserProfileScreen
import ru.potemkin.orpheusjetpackcompose.ui.theme.OrpheusJetpackComposeTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OrpheusJetpackComposeTheme {
                val component = getApplicationComponent()
                val viewModel: AuthViewModel = viewModel(factory = component.getViewModelFactory())
                var authState = viewModel.authState.observeAsState(AuthState.Initial)
                when (authState.value) {
                    is AuthState.Authorized -> {
                        MainScreen(this)
                    }

                    is AuthState.NotAuthorized -> {
                        val navigationState = rememberNavigationState()
                        Scaffold() {
                            AuthNavGraph(
                                navHostController = navigationState.navHostController,
                                loginScreenContent = {
                                    LoginScreen(
                                        paddingValues = it,
                                        onRegistrationClickListener = {
                                            navigationState.navigateToRegistration()
                                        },
                                        onNextClickListener = {
                                            navigationState.navigateToMainScreen() }
                                    )
                                },
                                registrationScreenContent = {
                                    RegistrationScreen(
                                        paddingValues = it,
                                        onLoginClickListener = { navigationState.navigateToLogin() },
                                        onNextClickListener = {
                                            navigationState.navigateToAboutMeScreen(it)
                                        }
                                    )
                                },
                                registrationAboutMeScreenContent = {
                                    AboutMeScreen(
                                        regItem = it,
                                        onBackPressed = { navigationState.navHostController.popBackStack() },
                                        onNextClickListener = {
                                            navigationState.navigateToUserTypeScreen(it)
                                        }
                                    )
                                },
                                registrationUserTypeScreenContent = {
                                    UserTypeScreen(
                                        aboutMeItem = it,
                                        onBackPressed = { navigationState.navHostController.popBackStack() },
                                        onAdminClickListener = {
                                            navigationState.navigateToAdministratorTypeScreen(
                                                it
                                            )
                                        },
                                        onMusicianClickListener = {
                                            navigationState.navigateToMusicianTypeScreen(
                                                it
                                            )
                                        }
                                    )
                                },
                                registrationMusicianTypeScreenContent = {
                                    MusicianRegScreen(typeItem = it,
                                        onBackPressed = {
                                            navigationState.navHostController.popBackStack()
                                        },
                                        onNextClickListener = {
                                            navigationState.navigateToMainScreen() }
                                    )
                                },
                                registrationAdministratorTypeScreenContent = {
                                    AdminRegScreen(typeItem = it,
                                        onBackPressed = {
                                            navigationState.navHostController.popBackStack()
                                        },
                                        onNextClickListener = {
                                            navigationState.navigateToMainScreen()})
                                },
                                startScreenContent = {
                                    StartScreen(
                                        onAuthClickListener = {
                                            navigationState.navigateToLogin()
                                        }
                                    )
                                },
                                mainScreenContent = {
                                    MainScreen(this)
                                }
                            )
                        }
                    }

                    else -> {

                    }
                }
            }
        }
    }
    
    
}




