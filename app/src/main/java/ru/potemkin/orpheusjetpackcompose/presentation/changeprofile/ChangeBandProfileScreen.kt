package ru.potemkin.orpheusjetpackcompose.presentation.changeprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.ui.theme.Black
import ru.potemkin.orpheusjetpackcompose.ui.theme.Grey
import ru.potemkin.orpheusjetpackcompose.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeBandProfileScreen(band: BandItem, onBackPressed: () -> Unit) {
    var bandName by remember { mutableStateOf(band.name) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {

                },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Назад",
                            tint = White
                        )
                    }
                },
                backgroundColor = Black,
                actions = {
                    IconButton(onClick = { /* открыть меню */ }) {
                        Icon(Icons.Default.Check,
                            contentDescription = "Сохранить",
                            tint = White)
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .background(Black)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Column {
                        androidx.compose.material3.Text(
                            text = "Изображения профиля",
                            style = MaterialTheme.typography.labelLarge,
                            color = White
                        )
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { }
                                .height(200.dp),
                            shape = RoundedCornerShape(
                                bottomEndPercent = 10,
                                bottomStartPercent = 10,
                                topEndPercent = 10,
                                topStartPercent = 10
                            ),
                            color = Black
                        ) {
                            AsyncImage(
                                model = band.photo.url,
                                modifier = Modifier.fillMaxSize(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = bandName,
                        onValueChange = { bandName = it },
                        label = { androidx.compose.material3.Text("Название") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Black,
                            unfocusedIndicatorColor = White,
                            focusedIndicatorColor = White,
                            focusedTextColor = White,
                            cursorColor = White,
                            unfocusedLabelColor = Grey,
                            unfocusedTextColor = Grey
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                }
            }
        },
        contentColor = Black
    )
}