package ru.potemkin.orpheusjetpackcompose.presentation.profile.otherusers

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
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.presentation.components.profile_comp.ProfileHeader
import ru.potemkin.orpheusjetpackcompose.presentation.components.profile_comp.ProfileTopBar
import ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.comments.CommentsScreenState
import ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.news.PostItem

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserProfileScreen(
    onBackPressed: () -> Unit,
    userItem: UserItem,
    onCommentClickListener: (PostItem) -> Unit,
) {
    val viewModel: UserProfileViewModel = viewModel(
        factory = UserProfileViewModelFactory(
            userItem
        )
    )
    val screenState = viewModel.screenState.observeAsState(UserProfileScreenState.Initial)
    val currentState = screenState.value
    val scrollState = rememberLazyListState()
    val topBarHeight = 56.dp // Замените на высоту вашего TopBar
    when (val currentState = screenState.value) {
        is UserProfileScreenState.User -> {
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

            ProfileTopBar({onBackPressed()})
        }

        UserProfileScreenState.Initial -> {}
        UserProfileScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.Black)
            }
        }
    }


}






