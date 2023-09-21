package ru.potemkin.orpheusjetpackcompose.presentation.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ru.potemkin.orpheusjetpackcompose.R
import ru.potemkin.orpheusjetpackcompose.presentation.components.AuthButton
import ru.potemkin.orpheusjetpackcompose.presentation.components.ButtonComponent
import ru.potemkin.orpheusjetpackcompose.presentation.components.TextEntryModule
import ru.potemkin.orpheusjetpackcompose.presentation.navigation.CHAT_LIST_SCREEN
import ru.potemkin.orpheusjetpackcompose.presentation.navigation.LOG_SCREEN
import ru.potemkin.orpheusjetpackcompose.presentation.theme.Green
import ru.potemkin.orpheusjetpackcompose.presentation.theme.White


@Composable
fun RegistrationScreen(
    navHostController: NavHostController
) {

    val surfaceVisible = remember { mutableStateOf(false) }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Green)
    ) {
        Column(Modifier.fillMaxSize()) {
            Row(Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "Orpheus",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
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
                    color = Color.White
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
                                color = Green
                            )
                        }
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            ButtonComponent(
                                text = "Зарегистрироваться",
                                backgroundColor = Green,
                                foregroundColor = White,
                                modifier = Modifier
                                    .padding(40.dp)
                                    .height(60.dp)

                            ) {
                                navHostController.navigate(CHAT_LIST_SCREEN)
                            }
                        }
                    }

                    Column(verticalArrangement = Arrangement.Bottom) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                "Есть аккаунт?",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(bottom = 15.dp)
                            )
                            Text(
                                "Войти!",
                                modifier = Modifier
                                    .clickable {
                                        navHostController.navigate(LOG_SCREEN)
                                    }
                                    .padding(bottom = 15.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                color = Green,
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
fun RegisterContainer(
    emailValue:() -> String,
    passwordValue:() -> String,
    passwordRepeatedValue:() -> String,
    buttonEnabled:() -> Boolean,
    onEmailChanged:(String)->Unit,
    onPasswordChanged:(String)->Unit,
    onPasswordRepeatedChanged:(String)->Unit,
    onButtonClick:()->Unit,
    isPasswordShown: ()->Boolean,
    isPasswordRepeatedShown: ()->Boolean,
    onTrailingPasswordIconClick: ()->Unit,
    onTrailingPasswordRepeatedIconClick: ()->Unit,
    errorHint:() -> String?,
    isLoading:() -> Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
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
                color = Color.Red,
                textAlign = TextAlign.Center
            )
            TextEntryModule(
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                description = "Введите почту",
                hint = "xd@xd.xd",
                leadingIcon = Icons.Default.Email,
                textValue = emailValue(),
                textColor = Green,
                cursorColor = Green,
                onValueChanged = onEmailChanged,
                trailingIcon = null,
                onTrailingIconClick = null
            )

            TextEntryModule(
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                description = "Пароль",
                hint = "qwerqwer",
                leadingIcon = Icons.Default.VpnKey,
                textValue = passwordValue(),
                textColor = Green,
                cursorColor = Green,
                onValueChanged = onPasswordChanged,
                keyboardType = KeyboardType.Password,
                visualTransformation = if (isPasswordShown()) {
                    VisualTransformation.None
                } else PasswordVisualTransformation(),
                trailingIcon = if (isPasswordShown()) Icons.Default.RemoveRedEye else Icons.Default.VisibilityOff,
                onTrailingIconClick = {
                    onTrailingPasswordIconClick()
                }
            )
            TextEntryModule(
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                description = "Повторите пароль",
                hint = "qwerqwer",
                leadingIcon = Icons.Default.VpnKey,
                textValue = passwordRepeatedValue(),
                textColor = Green,
                cursorColor = Green,
                onValueChanged = onPasswordRepeatedChanged,
                keyboardType = KeyboardType.Password,
                visualTransformation = if (isPasswordRepeatedShown()) {
                    VisualTransformation.None
                } else PasswordVisualTransformation(),
                trailingIcon = if (isPasswordShown()) Icons.Default.RemoveRedEye else Icons.Default.VisibilityOff,
                onTrailingIconClick = {
                    onTrailingPasswordRepeatedIconClick()
                }
            )
            AuthButton(
                text = "Заргеистрироваться",
                backgroundColor = Green,
                contentColor = Color.White,
                enabled = buttonEnabled(),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(10.dp)
                    .height(45.dp),
                onButtonClick = onButtonClick,
                isLoading = isLoading()
            )

        }
    }
}