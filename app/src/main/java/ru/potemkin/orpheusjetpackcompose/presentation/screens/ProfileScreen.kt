package ru.potemkin.orpheusjetpackcompose.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.potemkin.orpheusjetpackcompose.presentation.components.PostItem
import ru.potemkin.orpheusjetpackcompose.presentation.components.profile_comp.ProfileHeader
import ru.potemkin.orpheusjetpackcompose.presentation.components.profile_comp.ProfileTopBar
import ru.potemkin.orpheusjetpackcompose.presentation.components.user_profile_comp.UserProfileHeader
import ru.potemkin.orpheusjetpackcompose.presentation.components.user_profile_comp.UserProfileTopBar

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(navHostController: NavHostController) {
    var text by remember { mutableStateOf("") }
    val scrollState = rememberLazyListState()
    val topBarHeight = 56.dp // Замените на высоту вашего TopBar
    Scaffold(
        bottomBar = {
            ru.potemkin.orpheusjetpackcompose.presentation.components.BottomNavigationBar(
                navHostController = navHostController
            )
        },
        topBar = {
            ProfileTopBar(
                navHostController
            )
        }
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Здесь мы используем Modifier.graphicsLayer для анимации Header
            ProfileHeader(
                scrollState = scrollState,
                topBarHeight = topBarHeight
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = topBarHeight), // Учитываем высоту TopBar
                state = scrollState
            ) {
                items(10) { index ->
                    PostItem(index)
                }
            }

            ProfileTopBar(navHostController)
        }
    }
}






