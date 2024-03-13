package ru.potemkin.orpheusjetpackcompose.presentation.map.location

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorInfoItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorType
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.presentation.components.location_profile_comp.LocationProfileHeader
import ru.potemkin.orpheusjetpackcompose.presentation.components.location_profile_comp.LocationProfileTopBar

import ru.potemkin.orpheusjetpackcompose.presentation.post.PostItem
import ru.potemkin.orpheusjetpackcompose.presentation.profile.myprofile.CreatePostButton
import ru.potemkin.orpheusjetpackcompose.presentation.profile.otherusers.ChatButton
import ru.potemkin.orpheusjetpackcompose.ui.theme.Black

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LocationScreen(
    onBackPressed: () -> Unit,
    locationItem: LocationItem,
    paddingValues: PaddingValues,
    onUserClickListener: (UserItem) -> Unit,
    onCommentClickListener: (PostItem) -> Unit,
    onChatClickListener: (ChatItem) -> Unit,
    onChangeProfileClick: (LocationItem)->Unit,
    onPostCreateClickListener: (CreatorInfoItem) -> Unit,
) {
    val viewModel: LocationViewModel = viewModel(
        factory = LocationViewModelFactory(
            locationItem
        )
    )
    val screenState = viewModel.screenState.observeAsState(LocationScreenState.Initial)
    var text by remember { mutableStateOf("") }
    val scrollState = rememberLazyListState()
    val scaffoldState = rememberScaffoldState()
    val topBarHeight = 0.dp
    val currentUserIsAdmin = true
    when (val currentState = screenState.value) {
        is LocationScreenState.Location -> {
            androidx.compose.material.Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    LocationProfileTopBar(locationItem = currentState.location,
                        onBackPressed = onBackPressed,
                        currentUserIsAdmin = currentUserIsAdmin,
                        onChangeProfileClick = {onChangeProfileClick(currentState.location)}
                    )
                }
            ) {
                Box(modifier = Modifier.padding(paddingValues)) {
                    Column(
                        modifier = Modifier
                            .padding(it)
                    ) {
                        LocationProfileHeader(
                            topBarHeight = topBarHeight,
                            locationItem = currentState.location,
                            scrollState = scrollState,
                            onUserClickListener = onUserClickListener
                        )
                        if (!currentUserIsAdmin) {
                            ChatButton(
                                modifier = Modifier
                                    .padding(
                                        top = 4.dp,
                                        start = 40.dp,
                                        end = 40.dp,
                                        bottom = 4.dp
                                    )
                                    .height(40.dp),
                                onClick = { },
                                text = "Написать",
                                fontSize = 16.sp,
                                scrollState = scrollState
                            )
                        } else{
                            CreatePostButton(
                                modifier = Modifier
                                    .padding(
                                        top = 4.dp,
                                        start = 40.dp,
                                        end = 40.dp,
                                        bottom = 4.dp
                                    )
                                    .height(40.dp),
                                onClick = {
                                    onPostCreateClickListener(
                                        CreatorInfoItem(
                                            currentState.location.id,
                                            currentState.location.name,
                                            currentState.location.profilePicture,
                                            CreatorType.LOCATION
                                        )
                                    )
                                },
                                text = "Опубликовать",
                                fontSize = 16.sp,
                                scrollState = scrollState
                            )
                        }

                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = Black
                        ) {
                            Column {
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = topBarHeight),
                                    state = scrollState
                                ) {
                                    items(currentState.posts, key = { it.id }) { post ->
                                        PostItem(
                                            feedPost = post,
                                            onCommentClickListener = {
                                                onCommentClickListener(
                                                    post
                                                )
                                            },
                                            onLikeClickListener = { _ ->
                                                // viewModel.changeLikeStatus(feedPost)
                                            },
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        LocationScreenState.Initial -> {}
        LocationScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.Black)
            }
        }
    }
}



//@Preview(showBackground = true)
//@Composable
//fun LocationScreenPreview() {
//    OrpheusJetpackComposeTheme {
//       LocationScreen()
//    }
//}