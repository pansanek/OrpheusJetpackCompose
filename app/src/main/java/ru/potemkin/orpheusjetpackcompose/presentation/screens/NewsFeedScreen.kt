package ru.potemkin.orpheusjetpackcompose.presentation.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.potemkin.orpheusjetpackcompose.data.states.NewsScreenState
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.presentation.components.PostItem
import ru.potemkin.orpheusjetpackcompose.ui.theme.Green
import ru.potemkin.orpheusjetpackcompose.presentation.viewmodels.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewsFeedScreen(navHostController: NavHostController, newsViewModel: NewsViewModel) {
    var text by remember { mutableStateOf("") }

    val screenState = newsViewModel.screenState.observeAsState(NewsScreenState.Initial)


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Новости") },
                actions = {
                    IconButton(onClick = { /* Handle search click */ }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    }
                    IconButton(onClick = { /* Handle more options click */ }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                    }
                }
            )
        },
        bottomBar = {
            ru.potemkin.orpheusjetpackcompose.presentation.components.BottomNavigationBar(
                navHostController = navHostController
            )
        }
    ) {
        when (val currentState = screenState.value) {
            is NewsScreenState.Posts -> {
                FeedPosts(
                    text = "",
                    posts = currentState.posts,
                    viewModel = newsViewModel
                )
            }

            NewsScreenState.Initial -> {

            }
        }
    }
}

@Composable
private fun FeedPosts(
    text: String,
    posts: List<PostItem>,
    viewModel: NewsViewModel
) {
    var text1 = text
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Input field for creating new posts
        BasicTextField(
            value = text1,
            onValueChange = { newText -> text1 = newText },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Green),
            textStyle = MaterialTheme.typography.bodyLarge,
            singleLine = true,
            keyboardActions = KeyboardActions(
                onDone = {
                    // Handle post creation here
                    text1 = ""
                }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // News feed items
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(posts) { post ->
                PostItem(post, viewModel)
            }
        }
    }
}




