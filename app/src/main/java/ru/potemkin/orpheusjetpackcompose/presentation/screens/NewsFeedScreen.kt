package ru.potemkin.orpheusjetpackcompose.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.potemkin.orpheusjetpackcompose.presentation.components.PostItem
import ru.potemkin.orpheusjetpackcompose.presentation.theme.Green
import ru.potemkin.orpheusjetpackcompose.presentation.theme.OrpheusJetpackComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewsFeedScreen( navHostController: NavHostController) {
    var text by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Новости") },
                actions = {
                    IconButton(onClick = { /* Handle search click */ }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    }
                    IconButton(onClick = { /* Handle more options click */ }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                    }
                }
            )
        },
        bottomBar = {
            ru.potemkin.orpheusjetpackcompose.presentation.components.BottomNavigationBar(
                navHostController = navHostController
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Input field for creating new posts
            BasicTextField(
                value = text,
                onValueChange = { newText -> text = newText },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Green),
                textStyle = MaterialTheme.typography.bodyLarge,
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        // Handle post creation here
                        text = ""
                    }
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // News feed items
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(10) { index ->
                    PostItem(index)
                }
            }
        }
    }
}


