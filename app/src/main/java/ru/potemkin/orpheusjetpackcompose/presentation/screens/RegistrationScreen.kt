package ru.potemkin.orpheusjetpackcompose.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.presentation.components.ButtonComponent
import ru.potemkin.orpheusjetpackcompose.presentation.components.RadioButtonGroup
import ru.potemkin.orpheusjetpackcompose.presentation.navigation.ABOUT_ME_SCREEN
import ru.potemkin.orpheusjetpackcompose.presentation.navigation.ADM_REG_SCREEN
import ru.potemkin.orpheusjetpackcompose.presentation.navigation.CHAT_LIST_SCREEN
import ru.potemkin.orpheusjetpackcompose.presentation.navigation.LOG_SCREEN
import ru.potemkin.orpheusjetpackcompose.presentation.navigation.MUS_REG_SCREEN
import ru.potemkin.orpheusjetpackcompose.presentation.navigation.USER_TYPE_SCREEN
import ru.potemkin.orpheusjetpackcompose.presentation.theme.Green
import ru.potemkin.orpheusjetpackcompose.presentation.theme.White


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    navHostController: NavHostController
) {

    val surfaceVisible = remember { mutableStateOf(false) }

    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var aboutMe by remember { mutableStateOf("") }
    var userType by remember { mutableStateOf<UserType?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Green)
    ) {
        Column(Modifier.fillMaxSize()) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
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
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                        OutlinedTextField(
                            value = username,
                            onValueChange = { username = it },
                            label = { Text("Логин") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Пароль") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            visualTransformation = PasswordVisualTransformation()
                        )
                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            label = { Text("Повторите пароль") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            visualTransformation = PasswordVisualTransformation()
                        )
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            ButtonComponent(
                                text = "Далее",
                                backgroundColor = Green,
                                foregroundColor = White,
                                modifier = Modifier
                                    .padding(40.dp)
                                    .height(60.dp)

                            ) {
                                navHostController.navigate(ABOUT_ME_SCREEN)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutMeScreen(
    navHostController: NavHostController
) {
    var aboutMe by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize()) {
        Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.Center) {
            Text(
                text = "Расскажи о себе",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
        OutlinedTextField(
            value = aboutMe,
            onValueChange = { aboutMe = it },
            label = { Text("Дада я") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        // Кнопка для перехода на следующий экран
        ButtonComponent(
            text = "Продолжить",
            backgroundColor = Green,
            foregroundColor = White,
            modifier = Modifier
                .padding(16.dp)
                .height(60.dp),
            onClick = {
                navHostController.navigate(USER_TYPE_SCREEN)
            }

        )
    }
}

@Composable
fun UserTypeScreen(
    navHostController: NavHostController,
) {
    var userType by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize()) {

        Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.Center) {
            Text(
                text = "Выбери свой тип пользователя:",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
        val radioOptions = listOf("Музыкант", "Администратор базы", "Администратор площадки")
        RadioButtonGroup(
            options = radioOptions,
            selectedOption = userType,
            onOptionSelected = { userType = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        // Кнопка для завершения регистрации
        ButtonComponent(
            text = "Продолжить",
            backgroundColor = Green,
            foregroundColor = White,
            modifier = Modifier
                .padding(16.dp)
                .height(60.dp),
            onClick = {
                when (userType) {
                    "Музыкант" -> navHostController.navigate(MUS_REG_SCREEN)
                    "Администратор базы" -> navHostController.navigate(ADM_REG_SCREEN)
                    "Администратор площадки" -> navHostController.navigate(ADM_REG_SCREEN)
                }
            }
        )
    }
}


@Composable
fun MusicianRegScreen(
    navHostController: NavHostController,
) {
    Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.Center) {
        Text(
            text = "Выбери свой жанр и инструмент:",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
    var selectedInstrument by remember { mutableStateOf("") }
    var selectedGenre by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize()) {
        RadioButtonGroup(
            options = listOf("Гитара", "Фортепиано", "Ударные", "Скрипка", "Другое"),
            selectedOption = selectedInstrument,
            onOptionSelected = { selectedInstrument = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        // Выбор жанра
        RadioButtonGroup(
            options = listOf("Рок", "Джаз", "Классика", "Поп", "Другое"),
            selectedOption = selectedGenre,
            onOptionSelected = { selectedGenre = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        // Кнопка для завершения регистрации
        ButtonComponent(
            text = "Завершить регистрацию",
            backgroundColor = Green,
            foregroundColor = White,
            modifier = Modifier
                .padding(16.dp)
                .height(60.dp),
            onClick = {
                navHostController.navigate(CHAT_LIST_SCREEN)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminRegScreen(
    navHostController: NavHostController,
) {
    var address by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize()) {
        Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.Center) {
            Text(
                text = "Расскажи о своем месте",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Адрес") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        // Ввод названия
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Название") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        // Ввод описания
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Описание") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        // Кнопка для завершения регистрации
        ButtonComponent(
            text = "Завершить регистрацию",
            backgroundColor = Green,
            foregroundColor = White,
            modifier = Modifier
                .padding(16.dp)
                .height(60.dp),
            onClick = {navHostController.navigate(CHAT_LIST_SCREEN)}
        )
    }
}