package ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.presentation.components.NewsFeedDrawerBody
import ru.potemkin.orpheusjetpackcompose.presentation.components.NewsFeedDrawerHeader
import ru.potemkin.orpheusjetpackcompose.presentation.components.NewsFeedTopBar
import ru.potemkin.orpheusjetpackcompose.presentation.components.my_user_profile_comp.MenuItem
import ru.potemkin.orpheusjetpackcompose.presentation.components.my_user_profile_comp.MyUserProfileTopBar
import ru.potemkin.orpheusjetpackcompose.ui.theme.Black

@Composable
fun NewsFeedScreen(
    paddingValues: PaddingValues, onCommentClickListener: (PostItem) -> Unit
) {
    val viewModel: NewsFeedViewModel = viewModel()
    val screenState = viewModel.screenState.observeAsState(NewsFeedScreenState.Initial)

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    when (val currentState = screenState.value) {
        is NewsFeedScreenState.Posts -> {
            androidx.compose.material.Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    NewsFeedTopBar(onDrawerClickListener = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    })
                },
                drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
                drawerContent = {
                    NewsFeedDrawerHeader()
                    NewsFeedDrawerBody(
                        items = listOf(
                            MenuItem(
                                id = "help",
                                title = "Help",
                                contentDescription = "Get help",
                                icon = Icons.Default.Info
                            ),
                        ),
                        onItemClick = {

                        }
                    )
                },
                content = {
                    Box(
                        modifier = Modifier.fillMaxSize().background(Black),
                        contentAlignment = Alignment.Center
                    ) {
                        FeedPosts(
                            topPaddingValues = it,
                            bottomPaddingValues = paddingValues,
                            posts = currentState.posts,
                            onCommentClickListener = onCommentClickListener,
                            nextDataIsLoading = currentState.nextDataIsLoading
                        )
                    }
                }
            )
        }

        NewsFeedScreenState.Initial -> {}
        NewsFeedScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.Black)
            }
        }
    }
}


@Composable
private fun FeedPosts(
    topPaddingValues: PaddingValues,
    bottomPaddingValues: PaddingValues,
    posts: List<PostItem>,
    onCommentClickListener: (PostItem) -> Unit,
    nextDataIsLoading: Boolean
) {
    LazyColumn(
        modifier = Modifier.padding(
            top = topPaddingValues.calculateTopPadding(),
            bottom = bottomPaddingValues.calculateBottomPadding()
        ),
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = posts, key = { it.id }) { feedPost ->
            PostItem(
                feedPost = feedPost,
                onCommentClickListener = {
                    onCommentClickListener(feedPost)
                },
                onLikeClickListener = { _ ->
//                    viewModel.changeLikeStatus(feedPost)
                },
            )

        }
    }
}


