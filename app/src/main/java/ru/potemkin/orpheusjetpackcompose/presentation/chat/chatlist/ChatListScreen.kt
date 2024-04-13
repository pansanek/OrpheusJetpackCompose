package ru.potemkin.orpheusjetpackcompose.presentation.chat.chatlist

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import ru.potemkin.orpheusjetpackcompose.R
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.presentation.components.SpacerHeight
import ru.potemkin.orpheusjetpackcompose.presentation.components.SpacerWidth
import ru.potemkin.orpheusjetpackcompose.presentation.main.getApplicationComponent
import ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.news.NewsFeedScreenState
import ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.news.NewsFeedViewModel
import ru.potemkin.orpheusjetpackcompose.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatListScreen(
    paddingValues: PaddingValues,
    onChatClickListener: (ChatItem) -> Unit,
    onUserClickListener: (UserItem) -> Unit
) {
    val component = getApplicationComponent()
    val viewModel: ChatListViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.screenState.observeAsState(ChatListScreenState.Initial)
    when (val currentState = screenState.value) {
        is ChatListScreenState.Chats -> {
            Scaffold(
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Black)
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 30.dp)
                        ) {
                            CustomHeader()
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Color.White, RoundedCornerShape(
                                            topStart = 30.dp, topEnd = 30.dp
                                        )
                                    )
                            ) {
                                BottomSheetSwipeUp(
                                    modifier = Modifier
                                        .align(TopCenter)
                                        .padding(top = 15.dp)
                                )
                                LazyColumn(
                                    modifier = Modifier.padding(bottom = 15.dp, top = 30.dp)
                                ) {
                                    items(currentState.chats, key = { it.id }) {
                                        UserEachRow(
                                            lastMessage = it.lastMessage,
                                            chat = it,
                                            onChatClick = { onChatClickListener(it) },
                                            onUserClick= {onUserClickListener(it.users.get(1))}
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

            )
        }

        ChatListScreenState.Initial -> {}
        ChatListScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.Black)
            }
        }
    }


}

@Composable
fun CustomHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 20.dp)
    ) {
        Header()
    }
}


@Composable
fun BottomSheetSwipeUp(
    modifier: Modifier
) {

    Box(
        modifier = modifier
            .background(
                Black,
                RoundedCornerShape(90.dp)
            )
            .width(90.dp)
            .height(5.dp)

    )
}


@Composable
fun UserEachRow(
    lastMessage: String,
    chat: ChatItem,
    onChatClick: () -> Unit,
    onUserClick: () -> Unit,
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .noRippleEffect { onChatClick() }
            .padding(horizontal = 20.dp, vertical = 5.dp),
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    AsyncImage(
                        model = chat.picture.url,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .clickable {
                                onUserClick()
                            },
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                    SpacerWidth()
                    Column {
                        Text(
                            text = chat.name, style = TextStyle(
                                color = Color.Black, fontSize = 15.sp, fontWeight = FontWeight.Bold
                            )
                        )
                        SpacerHeight(5.dp)
                        Text(
                            text = lastMessage, style = TextStyle(
                                color = Black, fontSize = 14.sp
                            )
                        )
                    }

                }
            }
            SpacerHeight(15.dp)
            HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = Grey)
        }
    }

}


@Composable
fun Header() {

    Text(
        text = "Ваши чаты",
        style = MaterialTheme.typography.titleLarge,
        color = White,
        modifier = Modifier
            .padding(bottom = 32.dp)
    )

}

@SuppressLint("UnnecessaryComposedModifier", "UnrememberedMutableInteractionSource")
fun Modifier.noRippleEffect(onClick: () -> Unit) = composed {
    clickable(
        interactionSource = MutableInteractionSource(),
        indication = null
    ) {
        onClick()
    }
}





