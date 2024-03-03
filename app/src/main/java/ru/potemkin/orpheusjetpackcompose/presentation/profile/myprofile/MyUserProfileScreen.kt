package ru.potemkin.orpheusjetpackcompose.presentation.profile.myprofile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.navigation.rememberNavigationState
import ru.potemkin.orpheusjetpackcompose.presentation.components.my_user_profile_comp.DrawerBody
import ru.potemkin.orpheusjetpackcompose.presentation.components.my_user_profile_comp.DrawerHeader
import ru.potemkin.orpheusjetpackcompose.presentation.components.my_user_profile_comp.MenuItem
import ru.potemkin.orpheusjetpackcompose.presentation.components.my_user_profile_comp.MyUserProfileTopBar
import ru.potemkin.orpheusjetpackcompose.presentation.components.my_user_profile_comp.UserProfileHeader
import ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.news.PostItem
import ru.potemkin.orpheusjetpackcompose.ui.theme.Black


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyUserProfileScreen(
    paddingValues: PaddingValues,
    onSettingsClickListener: () -> Unit,
    onBandListClickListener: () -> Unit,
    onCommentClickListener: (PostItem) -> Unit,
) {
    val viewModel: MyUserProfileViewModel = viewModel()
    val screenState = viewModel.screenState.observeAsState(MyUserProfileScreenState.Initial)
    val scrollState = rememberLazyListState()

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val topBarHeight = 0.dp

    when (val currentState = screenState.value) {
        is MyUserProfileScreenState.User -> {
            androidx.compose.material.Scaffold(
                topBar = {
                    MyUserProfileTopBar(onSettingsClickListener = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    })
                },
                drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
                drawerContent = {
                    DrawerHeader()
                    DrawerBody(
                        items = listOf(
                            MenuItem(
                                id = "mybands",
                                title = "Мои группы",
                                contentDescription = "Переход к экрану групп пользователя",
                                icon = Icons.Default.Mic
                            ),
                            MenuItem(
                                id = "settings",
                                title = "Settings",
                                contentDescription = "Go to settings screen",
                                icon = Icons.Default.Settings
                            ),
                            MenuItem(
                                id = "help",
                                title = "Help",
                                contentDescription = "Get help",
                                icon = Icons.Default.Info
                            ),
                        ),
                        onItemClick = {
                           when(it.title){
                               "mybands" -> {onBandListClickListener()}
                               "settings" -> {onSettingsClickListener()}
                               else ->{ println("Clicked on ${it.title}")}
                           }
                        }
                    )
                },
                content = {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier
                                .padding(it)

                        ) {
                            UserProfileHeader(
                                currentState.user,
                                scrollState = scrollState,
                                topBarHeight = topBarHeight,
                                onBandListClickListener
                            )
                            Surface(
                                modifier = Modifier.fillMaxSize(),
                                color = Black
                            ) {
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = topBarHeight),
                                    state = scrollState
                                ) {
                                    items(currentState.posts, key = { it.id }) { post ->
                                        PostItem(
                                            feedPost = post,
                                            onCommentClickListener = { onCommentClickListener(post) },
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
            )
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


@Preview(showBackground = true)
@Composable
fun PreviewMyProfileScreen() {
    val navigationState = rememberNavigationState()
    MyUserProfileScreen(
        paddingValues = PaddingValues(),
        onSettingsClickListener = {},
        onBandListClickListener = {},
        onCommentClickListener = {}
    )
}



