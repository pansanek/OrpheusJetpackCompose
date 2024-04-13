package ru.potemkin.orpheusjetpackcompose.presentation.chat.chat

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import ru.potemkin.orpheusjetpackcompose.R
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.presentation.components.IconComponentImageVector
import ru.potemkin.orpheusjetpackcompose.presentation.components.SpacerWidth
import ru.potemkin.orpheusjetpackcompose.presentation.main.getApplicationComponent
import ru.potemkin.orpheusjetpackcompose.ui.theme.*
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    paddingValues: PaddingValues,
    onBackPressed: () -> Unit,
    chatItem: ChatItem,
    onUserClickListener: (UserItem) -> Unit
) {


    val component = getApplicationComponent()
        .getChatScreenComponentFactory().create(chatItem)
    val viewModel: ChatViewModel = viewModel(
        factory = component.getViewModelFactory()
    )
    val screenState = viewModel.screenState.observeAsState(ChatScreenState.Initial)
    val currentState = screenState.value
    val messages = viewModel.messagesFlow.collectAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    if (currentState is ChatScreenState.Messages) {
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .background(Black)

        ) {
            Scaffold(
                modifier = Modifier
                    .systemBarsPadding()
                    .fillMaxSize(),
                topBar = {
                    TopAppBar(
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    onBackPressed()
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Назад",
                                    tint = White
                                )
                            }
                        },
                        title = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                AsyncImage(
                                    model = chatItem.picture.url,
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .clickable {
                                            onUserClickListener(chatItem.users.get(1))
                                        },
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                )
                                SpacerWidth(width = 24.dp)
                                Column {
                                    Text(
                                        text = chatItem.name,
                                        style = TextStyle(
                                            color = White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp
                                        )
                                    )
                                }
                            }
                        },
                        backgroundColor = Black
                    )

                },
                bottomBar = {
                    CustomTextField(
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 20.dp),
                        viewModel = viewModel,
                        onSend = {
                            coroutineScope.launch {
                                listState.animateScrollToItem(messages.value.lastIndex)
                            }
                        }
                    )
                }, content = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Color.White, RoundedCornerShape(
                                    topStart = 30.dp, topEnd = 30.dp
                                )
                            )
                            .padding(top = 25.dp)
                    ) {
                        LazyColumn(
                            modifier = Modifier.padding(
                                start = 15.dp,
                                top = 45.dp,
                                end = 15.dp,
                                bottom = 75.dp
                            ),
                            state = listState
                        ) {
                            items(messages.value, key = { it.id }) {
                                ChatRow(message = it, viewModel = viewModel)
                            }
                        }
                    }
                }
            )
        }
    }

}

@Composable
fun ChatRow(
    message: MessageItem,
    viewModel: ChatViewModel
) {
    var userId = viewModel.getMyUserId()
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (message.fromUser.id != userId) Alignment.Start else Alignment.End
    ) {
        Box(
            modifier = Modifier
                .background(
                    if (message.fromUser.id != userId) Grey else Black,
                    RoundedCornerShape(100.dp)
                ),
            contentAlignment = Center
        ) {
            Text(
                text = message.content, style = TextStyle(
                    color = if (message.fromUser.id != userId) Black else White,
                    fontSize = 15.sp
                ),
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 15.dp),
                textAlign = TextAlign.End
            )
        }
        Text(
            text = message.timestamp,
            style = TextStyle(
                color = Black,
                fontSize = 12.sp
            ),
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 15.dp),
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    viewModel:ChatViewModel,
    onSend:()->Unit
) {
    var message by remember { mutableStateOf("") }

    TextField(
        value = message, onValueChange = { message=it },
        placeholder = {
            Text(
                text = stringResource(R.string.type_message),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = White
                ),
                textAlign = TextAlign.Center
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Black,
            unfocusedIndicatorColor = White,
            focusedIndicatorColor = White,
            focusedTextColor = White,
            cursorColor = White,


            ),
        leadingIcon = { CommonIconButton(imageVector = Icons.Default.Add,
            onClick = {}) },
        trailingIcon = {
            CommonIconButton(imageVector = Icons.Default.Send,
                onClick = {
                    onSend()
                    viewModel.sendMessage(message)
                })


        },
        modifier = modifier.fillMaxWidth().height(60.dp),
        shape = CircleShape
    )

}

@Composable
fun CommonIconButton(
    imageVector: ImageVector,
    onClick:()->Unit
) {
    Box(
        modifier = Modifier
            .background(White, CircleShape)
            .size(33.dp), contentAlignment = Center
    ) {
        IconComponentImageVector(icon = imageVector, size = 15.dp, tint = Black,
            modifier = Modifier.clickable {
                onClick()
            })
    }

}



@Composable
fun UserNameRow(
    modifier: Modifier = Modifier,
    user: UserItem
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,

        ) {
        Row {
            AsyncImage(
                model = user.profile_picture.url,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape),
                contentDescription = null
            )
            SpacerWidth()
            Column {
                Text(
                    text = user.name, style = TextStyle(
                        color = White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                )
            }
        }
    }

}
