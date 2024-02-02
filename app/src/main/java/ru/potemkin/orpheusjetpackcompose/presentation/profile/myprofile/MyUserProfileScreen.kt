package ru.potemkin.orpheusjetpackcompose.presentation.profile.myprofile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.presentation.components.my_user_profile_comp.MyUserProfileTopBar
import ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.news.PostItem
import ru.potemkin.orpheusjetpackcompose.presentation.components.my_user_profile_comp.UserProfileHeader
import ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.news.NewsFeedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyUserProfileScreen(
    paddingValues: PaddingValues,
    onSettingsClickListener: () -> Unit,
    onBandListClickListener: () -> Unit,
    onCommentClickListener: (PostItem) -> Unit,
) {
    val viewModel: NewsFeedViewModel = viewModel()
    val screenState = viewModel.screenState.observeAsState(MyUserProfileScreenState.Initial)
    val scrollState = rememberLazyListState()
    val topBarHeight = 48.dp // Замените на высоту вашего TopBar
    when (val currentState = screenState.value) {
        is MyUserProfileScreenState.User -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    // Здесь мы используем Modifier.graphicsLayer для анимации Header
                    UserProfileHeader(
                        scrollState = scrollState,
                        topBarHeight = topBarHeight,
                        { onBandListClickListener() }
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = topBarHeight), // Учитываем высоту TopBar
                        state = scrollState
                    ) {
                        items(currentState.posts, key = { it.id }) { post ->
                            PostItem(
                                feedPost = post,
                                onCommentClickListener = {
                                    onCommentClickListener(post)
                                },
                                onLikeClickListener = { _ ->
//                    viewModel.changeLikeStatus(feedPost)
                                },
                            )
                        }
                    }

                    MyUserProfileTopBar({ onSettingsClickListener() })
                }

            }
        }

        MyUserProfileScreenState.Initial -> {}
        MyUserProfileScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.Black)
            }
        }
    }

}





