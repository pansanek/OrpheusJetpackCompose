package ru.potemkin.orpheusjetpackcompose.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.potemkin.orpheusjetpackcompose.presentation.components.BottomNavigationBar
import ru.potemkin.orpheusjetpackcompose.presentation.components.PostItem
import ru.potemkin.orpheusjetpackcompose.presentation.components.band_profile_comp.BandProfileHeader
import ru.potemkin.orpheusjetpackcompose.presentation.components.band_profile_comp.BandProfileTopBar

import ru.potemkin.orpheusjetpackcompose.presentation.viewmodels.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BandProfileScreen(navHostController: NavHostController, newsViewModel: NewsViewModel) {
    var text by remember { mutableStateOf("") }
    val scrollState = rememberLazyListState()
    val topBarHeight = 56.dp // Замените на высоту вашего TopBar
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navHostController = navHostController
            )
        },
        topBar = {
            BandProfileTopBar(
                navHostController
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
                items(newsViewModel.postList) {post ->
                    PostItem(post,newsViewModel)
                }
            }

            BandProfileTopBar(navHostController)

        }
    }
}
