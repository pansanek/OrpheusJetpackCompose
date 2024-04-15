package ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.comments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import ru.potemkin.orpheusjetpackcompose.R
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.presentation.band.bandprofile.BandProfileViewModel
import ru.potemkin.orpheusjetpackcompose.presentation.chat.chat.ChatViewModel
import ru.potemkin.orpheusjetpackcompose.presentation.chat.chat.CommonIconButton
import ru.potemkin.orpheusjetpackcompose.presentation.main.getApplicationComponent
import ru.potemkin.orpheusjetpackcompose.ui.theme.Black
import ru.potemkin.orpheusjetpackcompose.ui.theme.Grey
import ru.potemkin.orpheusjetpackcompose.ui.theme.White

@Composable
fun CommentsScreen(
    onBackPressed: () -> Unit,
    postItem: PostItem,
    paddingValues: PaddingValues,
) {
    val component = getApplicationComponent()
        .getCommentsScreenComponentFactory()
        .create(postItem)
    val viewModel: CommentsViewModel = viewModel(
        factory = component.getViewModelFactory()
    )
    val screenState = viewModel.screenState.collectAsState(CommentsScreenState.Initial)
    val currentState = screenState.value
    val comments = viewModel.commentsFlow.collectAsState()
    if (currentState is CommentsScreenState.Comments) {
        Box(modifier = Modifier.padding(paddingValues)) {
            Scaffold(
                modifier = Modifier
                    .systemBarsPadding()
                    .fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = "Комментарии", color = White)
                        },
                        navigationIcon = {
                            IconButton(onClick = { onBackPressed() }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = null,
                                    tint = White
                                )
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
                        postItem = postItem
                    )
                }
            ) { paddingValues ->
                LazyColumn(
                    modifier = Modifier.padding(paddingValues),
                    contentPadding = PaddingValues(
                        top = 16.dp,
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 72.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(
                        items = comments.value,
                        key = { it.id }
                    ) { comment ->
                        CommentItem(comment = comment)
                    }
                }
            }
        }
    }
}

@Composable
private fun CommentItem(
    comment: CommentItem
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 4.dp
            )
    ) {
        AsyncImage(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
            ,
            model = comment.user.profile_picture.url,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = comment.user.name,
                color = Color.Black,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = comment.text,
                color = Color.Black,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = comment.timestamp,
                color = MaterialTheme.colors.onSecondary,
                fontSize = 12.sp
            )
        }
    }
    HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = Grey)
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    postItem: PostItem,
    viewModel: CommentsViewModel,
) {
    var message by remember { mutableStateOf("") }

    TextField(
        value = message, onValueChange = { message=it },
        placeholder = {
            androidx.compose.material3.Text(
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
        leadingIcon = {
            AsyncImage(
            model = viewModel.getMyUser().profile_picture.url,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .padding(4.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        ) },
        trailingIcon = {
            CommonIconButton(imageVector = Icons.Default.Send,
                onClick = {
                    viewModel.createComment(message, postItem)
                })


        },
        modifier = modifier.fillMaxWidth().height(60.dp),
        shape = CircleShape
    )

}