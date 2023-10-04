package ru.potemkin.orpheusjetpackcompose.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.potemkin.orpheusjetpackcompose.R
import ru.potemkin.orpheusjetpackcompose.presentation.components.ButtonComponent
import ru.potemkin.orpheusjetpackcompose.presentation.theme.Black
import ru.potemkin.orpheusjetpackcompose.presentation.theme.Green
import ru.potemkin.orpheusjetpackcompose.presentation.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserProfileScreen(navHostController: NavHostController) {
    var text by remember { mutableStateOf("") }
    Scaffold(
        bottomBar = {
            ru.potemkin.orpheusjetpackcompose.presentation.components.NavigationBar(
                navHostController = navHostController
            )
        }
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // User profile header
            UserProfileHeader()
            Spacer(modifier = Modifier.height(16.dp))

            // User posts
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(10) { index ->
                    UserProfilePost(index)
                }
            }
        }
    }
}

@Composable
fun UserProfileHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.sample9),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            // User profile picture
            Image(
                painter = painterResource(id = R.drawable.sample4),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .padding(4.dp)
                    .clip(MaterialTheme.shapes.small)
                    .border(
                        width = 2.dp,
                        color = White,
                        shape = MaterialTheme.shapes.small
                    ),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            // User display name
            Text(
                text = "@pansanek",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Саша",
                style = MaterialTheme.typography.labelLarge,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))
            // User bio
            Text(
                text = "реальный кабан",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Edit profile button
            ButtonComponent(
                text = "Редактировать профиль",
                backgroundColor = White,
                foregroundColor = Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)

            ) {

            }
        }
    }
}

@Composable
fun UserProfilePost(index: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Item $index",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Description for item $index",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}


