package ru.potemkin.orpheusjetpackcompose.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.potemkin.orpheusjetpackcompose.R
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.presentation.components.PostItem
import ru.potemkin.orpheusjetpackcompose.presentation.components.user_profile_comp.InviteButton
import ru.potemkin.orpheusjetpackcompose.presentation.components.user_profile_comp.UserProfileHeader
import ru.potemkin.orpheusjetpackcompose.presentation.components.user_profile_comp.UserProfileTopBar
import ru.potemkin.orpheusjetpackcompose.ui.theme.OrpheusJetpackComposeTheme
import ru.potemkin.orpheusjetpackcompose.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LocationScreen(
//    navHostController: NavHostController
) {
    var text by remember { mutableStateOf("") }
    val scrollState = rememberLazyListState()
    val topBarHeight = 56.dp // Замените на высоту вашего TopBar
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxSize()

            ) {
                androidx.compose.material.TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            androidx.compose.material.Text(text = "pansanek", color = White)
                            Spacer(modifier = Modifier.weight(1f)) // Занимаем всю доступную ширину
                            InviteButton()
                        }
                    },
                    navigationIcon = {
                        androidx.compose.material.IconButton(onClick = {
//                            navController.navigateUp()
                        }) {
                            androidx.compose.material.Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Назад",
                                tint = White
                            )
                        }
                    },
                    backgroundColor = Color.Black
                )
                // Добавьте функциональность для кнопки "Пригласить"

            }

        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Здесь мы используем Modifier.graphicsLayer для анимации Header
            val headerHeight by animateDpAsState(
                targetValue = if (scrollState.firstVisibleItemIndex > 0) 0.dp else 300.dp
            )
            val headerAlpha by animateFloatAsState(
                targetValue = if (scrollState.firstVisibleItemIndex > 0) 0f else 1f
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(headerHeight)
                    .graphicsLayer(alpha = headerAlpha)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.location1),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.medium)
                )

                // Текст в левом нижнем углу
                Column(
                    modifier = Modifier
                        .height(80.dp)
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(Alignment.BottomStart)
                ) {
                    Text(
                        text = "РЕПЕТИЦИОННАЯ БАЗА ДЛЯ КРУТЫХ РЕБЯТ",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(4.dp))


                    Text(
                        text = "УЛИЦА 3 ДОМ 4",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )
                }

                // Картинка в правом нижнем углу
                Image(
                    painter = painterResource(id = R.drawable.sample2),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(16.dp)
                        .clip(MaterialTheme.shapes.small)
                        .border(
                            width = 2.dp,
                            color = Color.White,
                            shape = MaterialTheme.shapes.small
                        )
                        .align(Alignment.BottomEnd),
                    contentScale = ContentScale.Crop
                )
            }

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

//            UserProfileTopBar(navHostController)

        }
    }
}



@Preview(showBackground = true)
@Composable
fun LocationScreenPreview() {
    OrpheusJetpackComposeTheme {
       LocationScreen()
    }
}