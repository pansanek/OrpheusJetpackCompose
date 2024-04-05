package ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorInfoItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorType
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.presentation.components.NewsFeedDrawerBody
import ru.potemkin.orpheusjetpackcompose.presentation.components.NewsFeedTopBar
import ru.potemkin.orpheusjetpackcompose.presentation.main.getApplicationComponent
import ru.potemkin.orpheusjetpackcompose.presentation.post.NewsFeedPostItem
import ru.potemkin.orpheusjetpackcompose.presentation.post.PostItem
import ru.potemkin.orpheusjetpackcompose.ui.theme.Black

@Composable
fun NewsFeedScreen(
    paddingValues: PaddingValues,
    onCommentClickListener: (PostItem) -> Unit,
    onPostCreateClickListener: (CreatorInfoItem) -> Unit,
    onLocationClickListener: (LocationItem) -> Unit,
    onUserClickListener: (UserItem) -> Unit,
    onBandClickListener: (BandItem) -> Unit
) {
    val component = getApplicationComponent()
    val viewModel: NewsFeedViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.screenState.observeAsState(NewsFeedScreenState.Initial)

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    when (val currentState = screenState.value) {
        is NewsFeedScreenState.Posts -> {
            androidx.compose.material.Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    NewsFeedTopBar(
                        onPostCreateClickListener = {
                            onPostCreateClickListener(
                                viewModel.getCreatorInfo()
                            )
                        },
                        onDrawerClickListener = {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }
                    )
                },
                drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
                drawerContent = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Black)
                            .padding(paddingValues),
                    ) {
                        Column {
                            NewsFeedDrawerBody(
                                items = currentState.notifications,
                                onUserClickListener = onUserClickListener,
                                onBandClickListener = onBandClickListener,
                            )
                        }
                    }
                },
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Black),
                        contentAlignment = Alignment.Center
                    ) {

                        FeedPosts(
                            viewModel = viewModel,
                            topPaddingValues = it,
                            bottomPaddingValues = paddingValues,
                            posts = currentState.posts,
                            onCommentClickListener = onCommentClickListener,
                            nextDataIsLoading = currentState.nextDataIsLoading,
                            onLocationClickListener = onLocationClickListener,
                            onBandClickListener = onBandClickListener,
                            onUserClickListener = onUserClickListener
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

        else -> {}
    }
}


@Composable
private fun FeedPosts(
    viewModel: NewsFeedViewModel,
    topPaddingValues: PaddingValues,
    bottomPaddingValues: PaddingValues,
    posts: List<PostItem>,
    onCommentClickListener: (PostItem) -> Unit,
    onLocationClickListener: (LocationItem) -> Unit,
    onUserClickListener: (UserItem) -> Unit,
    onBandClickListener: (BandItem) -> Unit,
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
            NewsFeedPostItem(
                viewModel = viewModel,
                feedPost = feedPost,
                onCommentClickListener = {
                    onCommentClickListener(feedPost)
                },
                onLocationClickListener = onLocationClickListener,
                onBandClickListener = onBandClickListener,
                onUserClickListener = onUserClickListener
            )

        }
    }
}


