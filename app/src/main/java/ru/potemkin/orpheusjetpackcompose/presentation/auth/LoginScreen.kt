package ru.potemkin.orpheusjetpackcompose.presentation.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.potemkin.orpheusjetpackcompose.navigation.rememberNavigationState
import ru.potemkin.orpheusjetpackcompose.presentation.components.AuthButton
import ru.potemkin.orpheusjetpackcompose.presentation.components.ButtonComponent
import ru.potemkin.orpheusjetpackcompose.presentation.components.PasswordEntryModule
import ru.potemkin.orpheusjetpackcompose.presentation.components.TextEntryModule
import ru.potemkin.orpheusjetpackcompose.presentation.main.getApplicationComponent
import ru.potemkin.orpheusjetpackcompose.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    paddingValues: PaddingValues,
    onRegistrationClickListener: () -> Unit,
    onNextClickListener: () -> Unit,
) {

    val surfaceVisible = remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val component = getApplicationComponent()
    val viewModel: AuthViewModel = viewModel(factory = component.getViewModelFactory())
    val authState = viewModel.authState.observeAsState(AuthState.Initial)

    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
    ) {
        Column(Modifier.fillMaxSize()) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "Orpheus",
                    style = MaterialTheme.typography.bodyLarge,
                    color = White,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Spacer(Modifier.height(5.dp))
            AnimatedVisibility(
                visible = surfaceVisible.value,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(10, 10),
                    color = White
                ) {
                    Column() {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            Text(
                                "Добро пожаловать!",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(top = 15.dp),
                                fontWeight = FontWeight.Bold,
                                fontSize = 32.sp,
                                textAlign = TextAlign.Center,
                                color = Black
                            )
                        }
                        TextEntryModule(
                            textValue = username,
                            onValueChanged = { username = it },
                            hint =  ("Логин") ,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            cursorColor = Black,
                            description = "Логин",
                            leadingIcon = Icons.Default.Person,
                            trailingIcon = null,
                            onTrailingIconClick = null,
                            textColor = Black
                        )
                        PasswordEntryModule(
                            textValue = password,
                            onValueChanged = { password = it },
                            hint = ("Пароль"),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            cursorColor = Black,
                            description = "Пароль",
                            leadingIcon = Icons.Default.Lock,
                            trailingIcon = Icons.Default.RemoveRedEye,
                            onTrailingIconClick = null,
                            textColor = Black
                        )
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            ButtonComponent(
                                text = "Войти",
                                backgroundColor = Black,
                                foregroundColor = White,
                                modifier = Modifier
                                    .padding(40.dp)
                                    .height(60.dp),
                                fontSize = 16.sp
                            ) {
                                viewModel.authorize(username, password)
                                when (authState.value) {
                                    is AuthState.Authorized -> {
                                        onNextClickListener()
                                    }

                                    else -> {

                                    }
                                }
                            }
                        }
                        Column(verticalArrangement = Arrangement.Bottom) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    "Нет аккаунта?",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Black,
                                    modifier = Modifier.padding(bottom = 15.dp)
                                )
                                Text(
                                    " Зарегистрироваться!",
                                    modifier = Modifier
                                        .clickable {
                                            onRegistrationClickListener()
                                        }
                                        .padding(bottom = 15.dp),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Black,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        LaunchedEffect(Unit) {
            surfaceVisible.value = true
        }

    }

    @Composable
    fun LoginContainer(
        emailValue: () -> String,
        passwordValue: () -> String,
        buttonEnabled: () -> Boolean,
        onEmailChanged: (String) -> Unit,
        onPasswordChanged: (String) -> Unit,
        onLoginButtonClick: () -> Unit,
        isPasswordShown: () -> Boolean,
        onTrailingPasswordIconClick: () -> Unit,
        errorHint: () -> String?,
        isLoading: () -> Boolean,
        modifier: Modifier = Modifier
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    errorHint() ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = Red,
                    textAlign = TextAlign.Center
                )

                TextEntryModule(
                    description = "Введите почту",
                    modifier = Modifier.fillMaxWidth(0.8f),
                    hint = "xd@xd.xd",
                    textValue = emailValue(),
                    textColor = White,
                    cursorColor = Black,
                    onValueChanged = onEmailChanged,
                    trailingIcon = null,
                    onTrailingIconClick = null,
                    leadingIcon = Icons.Default.Email
                )

                TextEntryModule(
                    description = "Введите пароль",
                    modifier = Modifier.fillMaxWidth(0.8f),
                    hint = "qwerqwer",
                    textValue = passwordValue(),
                    textColor = White,
                    cursorColor = Black,
                    onValueChanged = onPasswordChanged,
                    trailingIcon = if (isPasswordShown()) Icons.Default.RemoveRedEye else Icons.Default.VisibilityOff,
                    onTrailingIconClick = onTrailingPasswordIconClick,
                    leadingIcon = Icons.Default.VpnKey,
                    visualTransformation = if (isPasswordShown()) {
                        VisualTransformation.None
                    } else PasswordVisualTransformation(),
                    keyboardType = KeyboardType.Password
                )
                AuthButton(
                    text = "Войти",
                    backgroundColor = White,
                    contentColor = Black,
                    enabled = true,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(10.dp)
                        .height(45.dp),
                    isLoading = isLoading(),
                    onButtonClick = onLoginButtonClick
                )
            }
        }
    }
}
