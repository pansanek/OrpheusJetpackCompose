package ru.potemkin.orpheusjetpackcompose.presentation.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.news.PostItem
import ru.potemkin.orpheusjetpackcompose.presentation.components.profile_comp.ProfileHeader
import ru.potemkin.orpheusjetpackcompose.presentation.components.profile_comp.ProfileTopBar
import ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.news.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(navHostController: NavHostController,newsViewModel: NewsViewModel) {
    var text by remember { mutableStateOf("") }
    val scrollState = rememberLazyListState()
    val topBarHeight = 48.dp // Замените на высоту вашего TopBar
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
                items(newsViewModel.postList) {post ->
                    PostItem(post,newsViewModel)
                }
            }

            ProfileTopBar(navHostController)
        }
    }
}






