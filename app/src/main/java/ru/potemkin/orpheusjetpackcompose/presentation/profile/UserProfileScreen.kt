package ru.potemkin.orpheusjetpackcompose.presentation.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.news.PostItem
import ru.potemkin.orpheusjetpackcompose.presentation.components.user_profile_comp.UserProfileHeader
import ru.potemkin.orpheusjetpackcompose.presentation.components.user_profile_comp.UserProfileTopBar

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserProfileScreen(
    onBackPressed: () -> Unit,
    userItem: UserItem,
    onCommentClickListener: (PostItem) -> Unit,
) {
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
            UserProfileTopBar(
                navHostController
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Здесь мы используем Modifier.graphicsLayer для анимации Header
            UserProfileHeader(
                scrollState = scrollState,
                topBarHeight = topBarHeight
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = topBarHeight), // Учитываем высоту TopBar
                state = scrollState
            ) {
                items(newsViewModel.postList) { post ->
                    PostItem(post, newsViewModel)
                }
            }

            UserProfileTopBar(navHostController)

        }
    }
}






