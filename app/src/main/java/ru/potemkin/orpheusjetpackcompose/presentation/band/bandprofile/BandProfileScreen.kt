package ru.potemkin.orpheusjetpackcompose.presentation.band.bandprofile

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
import androidx.compose.runtime.collectAsState
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
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorInfoItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorType
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem

import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.presentation.components.StartAdminChatDialog
import ru.potemkin.orpheusjetpackcompose.presentation.components.StartBandChatDialog
import ru.potemkin.orpheusjetpackcompose.presentation.components.band_profile_comp.BandProfileHeader
import ru.potemkin.orpheusjetpackcompose.presentation.components.band_profile_comp.BandProfileTopBar
import ru.potemkin.orpheusjetpackcompose.presentation.main.getApplicationComponent
import ru.potemkin.orpheusjetpackcompose.presentation.post.PostItem
import ru.potemkin.orpheusjetpackcompose.presentation.profile.myprofile.CreatePostButton
import ru.potemkin.orpheusjetpackcompose.presentation.profile.otherusers.ChatButton
import ru.potemkin.orpheusjetpackcompose.ui.theme.Black

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BandProfileScreen(
    onBackPressed: () -> Unit,
    paddingValues: PaddingValues,
    bandItem: BandItem,
    onCommentClickListener: (PostItem) -> Unit,
    onUserClickListener: (UserItem) -> Unit,
    onChangeProfileClick: (BandItem)->Unit,
    onPostCreateClickListener: (CreatorInfoItem) -> Unit,
) {
    val component = getApplicationComponent()
        .getBandProfileScreenComponentFactory()
        .create(bandItem)
    val viewModel: BandProfileViewModel = viewModel(
        factory = component.getViewModelFactory()
    )
    val screenState = viewModel.screenState.collectAsState(BandProfileScreenState.Initial)
    val currentState = screenState.value
    var text by remember { mutableStateOf("") }
    val scrollState = rememberLazyListState()
    val scaffoldState = rememberScaffoldState()
    val topBarHeight = 0.dp
    val currentUserInBand = viewModel.isMyUserInBand()
    var startChat by remember { mutableStateOf(false) }
    when (val currentState = screenState.value) {
        is BandProfileScreenState.Band -> {
            androidx.compose.material.Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    BandProfileTopBar(
                        bandItem = currentState.band,
                        onBackPressed = onBackPressed,
                        currentUserInBand=currentUserInBand,
                        onChangeProfileClick = {onChangeProfileClick(bandItem)}
                    )
                }
            ) {
                Box(modifier = Modifier.padding(paddingValues)) {
                    Column(
                        modifier = Modifier
                            .padding(it)
                    ) {
                        BandProfileHeader(
                            topBarHeight = topBarHeight,
                            bandItem = currentState.band,
                            scrollState = scrollState,
                            onUserClickListener = onUserClickListener
                        )
                        if (currentUserInBand) {
                            ChatButton(
                                modifier = Modifier
                                    .padding(
                                        top = 4.dp,
                                        start = 40.dp,
                                        end = 40.dp,
                                        bottom = 4.dp
                                    )
                                    .height(40.dp),
                                onClick = { startChat = true},
                                text = "Написать",
                                fontSize = 16.sp,
                                scrollState = scrollState
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
                                onClick = {
                                    onPostCreateClickListener(
                                        CreatorInfoItem(
                                            currentState.band.id,
                                            currentState.band.name,
                                            currentState.band.photo,
                                            CreatorType.BAND
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

                                        )
                                    }
                                }
                            }
                        }
                        if (startChat) {
                            StartBandChatDialog(
                                band = bandItem,
                                onDismiss = { startChat = false },
                                onConfirm = {
                                    startChat = false
                                },
                                viewModel = viewModel)
                        }
                    }
                }
            }
        }

        BandProfileScreenState.Initial -> {}
        BandProfileScreenState.Loading -> {
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
//fun BandProfileScreenPreview() {
//    OrpheusJetpackComposeTheme {
//        BandProfileScreen()
//    }
//}