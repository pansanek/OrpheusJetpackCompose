package ru.potemkin.orpheusjetpackcompose.presentation.profile.myprofile

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DriveFileRenameOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.navigation.rememberNavigationState
import ru.potemkin.orpheusjetpackcompose.presentation.components.SpacerWidth
import ru.potemkin.orpheusjetpackcompose.presentation.components.my_user_profile_comp.DrawerBody
import ru.potemkin.orpheusjetpackcompose.presentation.components.my_user_profile_comp.DrawerHeader
import ru.potemkin.orpheusjetpackcompose.presentation.components.my_user_profile_comp.MenuItem
import ru.potemkin.orpheusjetpackcompose.presentation.components.my_user_profile_comp.MyUserProfileTopBar
import ru.potemkin.orpheusjetpackcompose.presentation.components.my_user_profile_comp.UserProfileHeader
import ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.news.PostItem
import ru.potemkin.orpheusjetpackcompose.ui.theme.Black
import ru.potemkin.orpheusjetpackcompose.ui.theme.LightBlack
import ru.potemkin.orpheusjetpackcompose.ui.theme.White


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyUserProfileScreen(
    paddingValues: PaddingValues,
    onSettingsClickListener: () -> Unit,
    onBandListClickListener: () -> Unit,
    onCommentClickListener: (PostItem) -> Unit,
) {
    val userType = UserType.MUSICIAN
    var menuItem = mutableMapOf<String, String>()
    var menuItemIcon = Icons.Default.Mic
    if (userType == UserType.MUSICIAN) {
        menuItem.put("mybands", "Мои группы")

    } else {
        menuItem.put("mylocations", "Мои учреждения")
        menuItemIcon = Icons.Default.LocationOn
    }

    val viewModel: MyUserProfileViewModel = viewModel()
    val screenState = viewModel.screenState.observeAsState(MyUserProfileScreenState.Initial)
    val scrollState = rememberLazyListState()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val topBarHeight = 0.dp

    when (val currentState = screenState.value) {
        is MyUserProfileScreenState.User -> {
            androidx.compose.material.Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    MyUserProfileTopBar(user = currentState.user, onDrawerClickListener = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    })
                },
                drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
                drawerContent = {
                    DrawerHeader(currentState.user)
                    DrawerBody(
                        items = listOf(
                            MenuItem(
                                id = menuItem.keys.elementAt(0),
                                title = menuItem.getValue(menuItem.keys.elementAt(0)),
                                contentDescription =
                                "Переход к экрану " +
                                        "${menuItem.getValue(menuItem.keys.elementAt(0))}",
                                icon = menuItemIcon
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
                            when (it.id) {
                                "mybands" -> onBandListClickListener()
                                "settings" -> onSettingsClickListener()
                                else -> {
                                    println("Clicked on ${it.title}")
                                }
                            }
                        }
                    )
                },
                content = {
                    Box(modifier = Modifier.padding(paddingValues)) {
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
                            CreatePostButton(
                                modifier = Modifier
                                    .padding(
                                        top = 4.dp,
                                        start = 40.dp,
                                        end = 40.dp,
                                        bottom = 4.dp
                                    )
                                    .height(40.dp),
                                onClick = { },
                                text = "Опубликовать",
                                fontSize = 16.sp,
                                scrollState = scrollState
                            )
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

@Composable
fun CreatePostButton(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color = LightBlack,
    foregroundColor: Color = White,
    elevation: ButtonElevation = ButtonDefaults.elevatedButtonElevation(0.dp),
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = backgroundColor
    ),
    fontSize: TextUnit,
    onClick: () -> Unit,
    scrollState: LazyListState,
) {
    val headerHeight by animateDpAsState(
        targetValue = if (scrollState.firstVisibleItemIndex > 0) 0.dp else 50.dp
    )
    val headerAlpha by animateFloatAsState(
        targetValue = if (scrollState.firstVisibleItemIndex > 0) 0f else 1f
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(headerHeight)
            .graphicsLayer(alpha = headerAlpha)
            .background(Black),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { },
            modifier = modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(100.dp),
            elevation = elevation,
            colors = colors,
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Добавить пост",
                tint = foregroundColor
            )
            SpacerWidth()
            Text(
                text = text, style = TextStyle(
                    color = foregroundColor,
                    fontSize = fontSize,
                    fontWeight = FontWeight.Bold
                )
            )
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



