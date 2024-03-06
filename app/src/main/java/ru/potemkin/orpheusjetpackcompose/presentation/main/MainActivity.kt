package ru.potemkin.orpheusjetpackcompose.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.navigation.rememberNavigationState
import ru.potemkin.orpheusjetpackcompose.presentation.auth.AuthState
import ru.potemkin.orpheusjetpackcompose.presentation.auth.AuthViewModel
import ru.potemkin.orpheusjetpackcompose.presentation.auth.StartScreen
import ru.potemkin.orpheusjetpackcompose.presentation.profile.otherusers.UserProfileScreen
import ru.potemkin.orpheusjetpackcompose.ui.theme.OrpheusJetpackComposeTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as MainApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
//        MapKitFactory.setApiKey("YOUR_API_KEY")
        setContent {
            OrpheusJetpackComposeTheme {
                val viewModel: AuthViewModel = viewModel(factory = viewModelFactory)
                val authState = viewModel.authState.observeAsState(AuthState.Initial)
                val navigationState = rememberNavigationState()
                when (authState.value) {
                    is AuthState.Authorized -> {
                        MainScreen(viewModelFactory)
                    }

                    is AuthState.NotAuthorized -> {
                        StartScreen(
                            onAuthClickListener = {
                                navigationState.navigateToLogin()
                            }
                        )
                    }
                    else -> {

                    }
                }
            }
        }
    }
}




