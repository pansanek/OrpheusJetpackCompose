package ru.potemkin.orpheusjetpackcompose.presentation.band

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.presentation.components.band_profile_comp.BandProfileHeader
import ru.potemkin.orpheusjetpackcompose.presentation.components.band_profile_comp.BandProfileTopBar

import ru.potemkin.orpheusjetpackcompose.ui.theme.OrpheusJetpackComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BandProfileScreen(
    onBackPressed: () -> Unit,
    bandItem: BandItem,
    onCommentClickListener: (PostItem) -> Unit,
    onUserClickListener: (UserItem) -> Unit
) {
    var text by remember { mutableStateOf("") }
    val scrollState = rememberLazyListState()
    val topBarHeight = 56.dp // Замените на высоту вашего TopBar
    Scaffold(
//        bottomBar = {
//            BottomNavigationBar(
////                navHostController = navHostController
//            )
//        },
        topBar = {
            BandProfileTopBar(
//                navHostController
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Здесь мы используем Modifier.graphicsLayer для анимации Header
            BandProfileHeader(
                scrollState = scrollState,
                topBarHeight = topBarHeight
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = topBarHeight), // Учитываем высоту TopBar
                state = scrollState
            ) {
//                items(newsViewModel.postList) {post ->
//                    PostItem(post,newsViewModel)
//                }
            }

            BandProfileTopBar(
//                navHostController
            )

        }
    }
}


@Preview(showBackground = true)
@Composable
fun BandProfileScreenPreview() {
    OrpheusJetpackComposeTheme {
        BandProfileScreen()
    }
}