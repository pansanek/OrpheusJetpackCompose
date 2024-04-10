package ru.potemkin.orpheusjetpackcompose.presentation.profile.otherusers

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.presentation.components.SpacerWidth
import ru.potemkin.orpheusjetpackcompose.presentation.components.StartChatDialog
import ru.potemkin.orpheusjetpackcompose.presentation.components.profile_comp.ProfileHeader
import ru.potemkin.orpheusjetpackcompose.presentation.components.profile_comp.ProfileTopBar
import ru.potemkin.orpheusjetpackcompose.presentation.main.getApplicationComponent
import ru.potemkin.orpheusjetpackcompose.presentation.map.map.CustomAlertDialog
import ru.potemkin.orpheusjetpackcompose.presentation.post.PostItem
import ru.potemkin.orpheusjetpackcompose.ui.theme.Black
import ru.potemkin.orpheusjetpackcompose.ui.theme.LightBlack
import ru.potemkin.orpheusjetpackcompose.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserProfileScreen(
    paddingValues: PaddingValues,
    onBackPressed: () -> Unit,
    userItem: UserItem,
    onCommentClickListener: (PostItem) -> Unit,
    onBandClickListener: (BandItem) -> Unit,
    onLocationClickListener: (LocationItem) -> Unit,
) {
    val component = getApplicationComponent()
        .getUserProfileScreenComponentFactory()
        .create(userItem)
    val viewModel: UserProfileViewModel = viewModel(
        factory = component.getViewModelFactory()
    )
    val screenState = viewModel.screenState.observeAsState(UserProfileScreenState.Initial)
    val scrollState = rememberLazyListState()
    val topBarHeight = 0.dp
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var startChat by remember { mutableStateOf(false) }
    when (val currentState = screenState.value) {
        is UserProfileScreenState.User -> {
            androidx.compose.material.Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    ProfileTopBar(
                        userItem = currentState.user,
                        onBackPressed = onBackPressed,
                        viewModel = viewModel
                    )
                }
            ) {
                Box(modifier = Modifier.padding(paddingValues)) {
                    Column(
                        modifier = Modifier
                            .padding(it)
                    ) {
                        ProfileHeader(
                            currentState.user,
                            scrollState = scrollState,
                            onBandClickListener,
                            onLocationClickListener,
                            currentState,
                        )
                        ChatButton(
                            modifier = Modifier
                                .padding(
                                    top = 4.dp,
                                    start = 40.dp,
                                    end = 40.dp,
                                    bottom = 4.dp
                                )
                                .height(40.dp),
                            onClick = { startChat = true },
                            text = "Написать",
                            fontSize = 16.sp,
                            scrollState = scrollState,
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
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                    if (startChat) {
                        StartChatDialog(
                            toUser = currentState.user,
                            onDismiss = { startChat = false },
                            onConfirm = {
                                startChat = false
                            },
                            viewModel = viewModel)
                    }
                }
            }
        }

        UserProfileScreenState.Initial -> {}
        UserProfileScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Black)
            }
        }
    }


}


@Composable
fun ChatButton(
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
            onClick = onClick,
            modifier = modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(100.dp),
            elevation = elevation,
            colors = colors,
        ) {
            Icon(
                imageVector = Icons.Default.Chat,
                contentDescription = "написать",
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


