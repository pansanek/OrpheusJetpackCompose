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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DriveFileRenameOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.potemkin.orpheusjetpackcompose.domain.entities.AuthItems.AboutMeItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.AuthItems.RegItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.AuthItems.TypeItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem

import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.navigation.rememberNavigationState
import ru.potemkin.orpheusjetpackcompose.presentation.components.ButtonComponent
import ru.potemkin.orpheusjetpackcompose.presentation.components.PasswordEntryModule
import ru.potemkin.orpheusjetpackcompose.presentation.components.RadioButtonGroup
import ru.potemkin.orpheusjetpackcompose.presentation.components.TextEntryModule
import ru.potemkin.orpheusjetpackcompose.ui.theme.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    paddingValues: PaddingValues,
    onLoginClickListener: () -> Unit,
    onNextClickListener: (RegItem) -> Unit
) {

    val surfaceVisible = remember { mutableStateOf(false) }

    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }


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
                    color = Green,
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
                            textValue = email,
                            onValueChanged = { email = it },
                            hint = ("Почта"),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp, horizontal = 16.dp),
                            cursorColor = Black,
                            description = "Почта",
                            leadingIcon = Icons.Default.Email,
                            trailingIcon = null,
                            onTrailingIconClick = null,
                            textColor = Black
                        )
                        TextEntryModule(
                            textValue = name,
                            onValueChanged = { name = it },
                            hint = ("Ваше имя"),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp, horizontal = 16.dp),
                            cursorColor = Black,
                            description = "Ваше имя",
                            leadingIcon = Icons.Default.DriveFileRenameOutline,
                            trailingIcon = null,
                            onTrailingIconClick = null,
                            textColor = Black
                        )
                        TextEntryModule(
                            textValue = username,
                            onValueChanged = { username = it },
                            hint = ("Логин"),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp, horizontal = 16.dp),
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
                                .padding(vertical = 4.dp, horizontal = 16.dp),
                            cursorColor = Black,
                            description = "Пароль",
                            leadingIcon = Icons.Default.Lock,
                            trailingIcon = Icons.Default.RemoveRedEye,
                            onTrailingIconClick = null,
                            textColor = Black
                        )
                        PasswordEntryModule(
                            textValue = confirmPassword,
                            onValueChanged = { confirmPassword = it },
                            hint = ("Повторите пароль"),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp, horizontal = 16.dp),
                            cursorColor = Black,
                            description = "Повторите пароль",
                            leadingIcon = Icons.Default.Lock,
                            trailingIcon = Icons.Default.RemoveRedEye,
                            onTrailingIconClick = null,
                            textColor = Black
                        )
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            ButtonComponent(
                                text = "Далее",
                                backgroundColor = Black,
                                foregroundColor = White,
                                modifier = Modifier
                                    .padding(40.dp)
                                    .height(60.dp),
                                fontSize = 16.sp
                            ) {
                                onNextClickListener(RegItem(email, username, name, password))
                            }
                        }
                    }

                    Column(verticalArrangement = Arrangement.Bottom) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                "Есть аккаунт? ",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Black,
                                modifier = Modifier.padding(bottom = 32.dp)
                            )
                            Text(
                                "Войти!",
                                modifier = Modifier
                                    .clickable {
                                        onLoginClickListener()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutMeScreen(
    regItem: RegItem,
    onBackPressed: () -> Unit,
    onNextClickListener: (AboutMeItem) -> Unit
) {
    var aboutMe by remember { mutableStateOf("") }
    val surfaceVisible = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
    ) {
        Column(Modifier.fillMaxSize()) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Расскажи о себе",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Green,
                    fontSize = 24.sp,
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
                        TextEntryModule(
                            textValue = aboutMe,
                            onValueChanged = { aboutMe = it },
                            hint = ("О себе"),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            height = 160.dp,
                            cursorColor = Black,
                            description = "О себе",
                            singleLine = false,
                            leadingIcon = null,
                            trailingIcon = null,
                            onTrailingIconClick = null,
                            textColor = Black
                        )
                    }
                    Column(
                        modifier = Modifier
                            .padding(32.dp),
                        verticalArrangement = Arrangement.Bottom,
                    ) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            ButtonComponent(
                                text = "Продолжить",
                                backgroundColor = Black,
                                foregroundColor = White,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .height(60.dp),
                                fontSize = 24.sp,
                                onClick = {
                                    onNextClickListener(AboutMeItem(regItem, aboutMe))
                                }
                            )
                        }
                    }
                }
            }
            LaunchedEffect(Unit) {
                surfaceVisible.value = true
            }
        }
    }
}

@Composable
fun UserTypeScreen(
    aboutMeItem: AboutMeItem,
    onBackPressed: () -> Unit,
    onAdminClickListener: (TypeItem) -> Unit,
    onMusicianClickListener: (TypeItem) -> Unit
) {
    var userType by remember { mutableStateOf("") }
    val surfaceVisible = remember { mutableStateOf(false) }
    val radioOptions =
        listOf("Музыкант", "Администратор музыкального учреждения")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
    ) {
        Column(Modifier.fillMaxSize()) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Выбери свой тип пользователя",
                    style = MaterialTheme.typography.bodyLarge,
                    color = White,
                    fontSize = 24.sp,
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
                        RadioButtonGroup(
                            options = radioOptions,
                            selectedOption = userType,
                            onOptionSelected = { userType = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                    Column(
                        modifier = Modifier
                            .padding(32.dp),
                        verticalArrangement = Arrangement.Bottom,
                    ) {
                        ButtonComponent(
                            text = "Продолжить",
                            backgroundColor = Black,
                            foregroundColor = White,
                            modifier = Modifier
                                .height(60.dp),
                            fontSize = 24.sp,
                            onClick = {
                                when (userType) {
                                    "Музыкант" -> {
                                        onMusicianClickListener(
                                            TypeItem(
                                                aboutMeItem,
                                                UserType.MUSICIAN
                                            )
                                        )
                                    }

                                    "Администратор музыкального учреждения" -> {
                                        onAdminClickListener(
                                            TypeItem(
                                                aboutMeItem,
                                                UserType.ADMINISTRATOR
                                            )
                                        )
                                    }
                                }
                            }
                        )
                    }
                }

            }
        }
        LaunchedEffect(Unit) {
            surfaceVisible.value = true
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MusicianRegScreen(
    typeItem: TypeItem,
    onBackPressed: () -> Unit,
    onNextClickListener: () -> Unit
) {
    var selectedInstrument by remember { mutableStateOf("Инструмент") }
    var selectedGenre by remember { mutableStateOf("Жанр") }
    var expandedInstrument by remember { mutableStateOf(false) }
    var expandedGenre by remember { mutableStateOf(false) }

    val viewModel: AuthViewModel = viewModel()
    val surfaceVisible = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
    ) {
        Column(Modifier.fillMaxSize()) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Выбери свой жанр и инструмент",
                    style = MaterialTheme.typography.bodyLarge,
                    color = White,
                    fontSize = 24.sp,
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
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 32.dp)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ExposedDropdownMenuBox(
                            expanded = expandedInstrument,
                            onExpandedChange = {
                                expandedInstrument = !expandedInstrument
                            }
                        ) {
                            TextField(
                                value = selectedInstrument,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedInstrument) },
                                modifier = Modifier.menuAnchor(),
                                colors = ExposedDropdownMenuDefaults.textFieldColors(
                                    textColor = Black,
                                    backgroundColor = White,
                                    trailingIconColor = Black,
                                    focusedTrailingIconColor = Black,
                                    focusedIndicatorColor = Black,
                                    unfocusedIndicatorColor = Black,
                                    focusedLabelColor = Black,
                                    unfocusedLabelColor = Black,
                                    leadingIconColor = Black,
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = expandedInstrument,
                                onDismissRequest = { expandedInstrument = false },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                listOf(
                                    "Гитара",
                                    "Фортепиано",
                                    "Ударные",
                                    "Скрипка",
                                    "Вокал",
                                ).forEach { instrument ->
                                    DropdownMenuItem(
                                        text = { Text(text = instrument) },
                                        onClick = {
                                            selectedInstrument = instrument
                                            expandedInstrument = false
                                        },
                                    )
                                }
                            }

                        }
                        Spacer(Modifier.height(24.dp))
                        ExposedDropdownMenuBox(
                            expanded = expandedGenre,
                            onExpandedChange = {
                                expandedGenre = !expandedGenre
                            },
                        ) {
                            TextField(
                                value = selectedGenre,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedGenre) },
                                modifier = Modifier.menuAnchor(),
                                colors = ExposedDropdownMenuDefaults.textFieldColors(
                                    textColor = Black,
                                    backgroundColor = White,
                                    trailingIconColor = Black,
                                    focusedTrailingIconColor = Black,
                                    focusedIndicatorColor = Black,
                                    unfocusedIndicatorColor = Black,
                                    focusedLabelColor = Black,
                                    unfocusedLabelColor = Black,
                                    leadingIconColor = Black,
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = expandedGenre,
                                onDismissRequest = { expandedGenre = false },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                listOf(
                                    "Рок",
                                    "Джаз",
                                    "Классика",
                                    "Поп",
                                    "Метал"
                                ).forEach { genre ->
                                    DropdownMenuItem(
                                        text = { Text(text = genre) },
                                        onClick = {
                                            selectedGenre = genre
                                            expandedGenre = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                    Column(
                        modifier = Modifier
                            .padding(32.dp),
                        verticalArrangement = Arrangement.Bottom,
                    ) {
                        ButtonComponent(
                            text = "Завершить регистрацию",
                            backgroundColor = Black,
                            foregroundColor = White,
                            modifier = Modifier
                                .padding(16.dp)
                                .height(60.dp),
                            fontSize = 16.sp,
                            onClick = {
                                viewModel.createMusician(
                                    about = typeItem.aboutMeItem.aboutMe,
                                    email = typeItem.aboutMeItem.regItem.email,
                                    login = typeItem.aboutMeItem.regItem.username,
                                    password = typeItem.aboutMeItem.regItem.password,
                                    name = typeItem.aboutMeItem.regItem.name,
                                    userType = typeItem.userType.toString(),
                                    genre = selectedGenre,
                                    instrument = selectedInstrument
                                )
                                onNextClickListener()
                            }
                        )
                    }
                }
            }
            LaunchedEffect(Unit) {
                surfaceVisible.value = true
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminRegScreen(
    typeItem: TypeItem,
    onBackPressed: () -> Unit,
    onNextClickListener: () -> Unit
) {
    var locationAddress by remember { mutableStateOf("") }
    var locationName by remember { mutableStateOf("") }
    var locationAbout by remember { mutableStateOf("") }

    val viewModel: AuthViewModel = viewModel()

    val surfaceVisible = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
    ) {
        Column(Modifier.fillMaxSize()) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Расскажи о своем месте",
                    style = MaterialTheme.typography.bodyLarge,
                    color = White,
                    fontSize = 24.sp,
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
                    Column(Modifier.fillMaxSize()) {
                        TextEntryModule(
                            textValue = locationAddress,
                            onValueChanged = { locationAddress = it },
                            hint = ("Адрес"),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp)
                                .padding(16.dp),
                            cursorColor = Black,
                            description = "Адрес",
                            singleLine = false,
                            leadingIcon = Icons.Default.LocationOn,
                            trailingIcon = null,
                            onTrailingIconClick = null,
                            textColor = Black
                        )

                        TextEntryModule(
                            textValue = locationName,
                            onValueChanged = { locationName = it },
                            hint = ("Название"),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp)
                                .padding(16.dp),
                            cursorColor = Black,
                            description = "Название",
                            singleLine = false,
                            leadingIcon = Icons.Default.Edit,
                            trailingIcon = null,
                            onTrailingIconClick = null,
                            textColor = Black
                        )

                        // Ввод описания
                        TextEntryModule(
                            textValue = locationAbout,
                            onValueChanged = { locationAbout = it },
                            hint = ("Описание"),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            height = 160.dp,
                            cursorColor = Black,
                            description = "Описание",
                            singleLine = false,
                            leadingIcon = null,
                            trailingIcon = null,
                            onTrailingIconClick = null,
                            textColor = Black
                        )

                        // Кнопка для завершения регистрации
                        ButtonComponent(
                            text = "Завершить регистрацию",
                            backgroundColor = Black,
                            foregroundColor = White,
                            modifier = Modifier
                                .padding(16.dp)
                                .height(60.dp),
                            fontSize = 16.sp,
                            onClick = {
                                viewModel.createAdminAndLocation(
                                    about = typeItem.aboutMeItem.aboutMe,
                                    email = typeItem.aboutMeItem.regItem.email,
                                    login = typeItem.aboutMeItem.regItem.username,
                                    password = typeItem.aboutMeItem.regItem.password,
                                    name = typeItem.aboutMeItem.regItem.name,
                                    userType = typeItem.userType.toString(),
                                    locationName = locationName,
                                    locationAbout = locationAbout,
                                    locationAddress = locationAddress
                                )
                                onNextClickListener()
                            }
                        )
                    }
                }
            }
            LaunchedEffect(Unit) {
                surfaceVisible.value = true
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewRegistrationScreen() {
    val navigationState = rememberNavigationState()
    AdminRegScreen(
        typeItem = TypeItem(AboutMeItem(RegItem("a", "a", "a", "a"), "a"), UserType.ADMINISTRATOR),
        onBackPressed = { /*TODO*/ },
        onNextClickListener = {})
}