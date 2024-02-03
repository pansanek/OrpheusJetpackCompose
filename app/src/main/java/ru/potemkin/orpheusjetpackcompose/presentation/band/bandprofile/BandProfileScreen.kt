package ru.potemkin.orpheusjetpackcompose.presentation.band.bandprofile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem

import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.presentation.components.band_profile_comp.BandProfileHeader
import ru.potemkin.orpheusjetpackcompose.presentation.components.band_profile_comp.BandProfileTopBar
import ru.potemkin.orpheusjetpackcompose.presentation.components.profile_comp.ProfileHeader
import ru.potemkin.orpheusjetpackcompose.presentation.components.profile_comp.ProfileTopBar
import ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.comments.CommentsScreenState
import ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.news.PostItem
import ru.potemkin.orpheusjetpackcompose.presentation.profile.otherusers.UserProfileScreenState
import ru.potemkin.orpheusjetpackcompose.presentation.profile.otherusers.UserProfileViewModel
import ru.potemkin.orpheusjetpackcompose.presentation.profile.otherusers.UserProfileViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BandProfileScreen(
    onBackPressed: () -> Unit,
    bandItem: BandItem,
    onCommentClickListener: (PostItem) -> Unit,
    onUserClickListener: (UserItem) -> Unit
) {
    val viewModel: BandProfileViewModel = viewModel(
        factory = BandProfileViewModelFactory(
            bandItem
        )
    )
    val screenState = viewModel.screenState.observeAsState(BandProfileScreenState.Initial)
    val currentState = screenState.value
    var text by remember { mutableStateOf("") }
    val scrollState = rememberLazyListState()
    val topBarHeight = 56.dp // Замените на высоту вашего TopBar
    when (val currentState = screenState.value) {
        is BandProfileScreenState.Band -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // Здесь мы используем Modifier.graphicsLayer для анимации Header
                BandProfileHeader(
                    scrollState = scrollState,
                    topBarHeight = topBarHeight,
                    bandItem.members
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = topBarHeight), // Учитываем высоту TopBar
                    state = scrollState
                ) {
                    items(currentState.posts) { post ->
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

                BandProfileTopBar(
                    { onBackPressed() }
                )

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