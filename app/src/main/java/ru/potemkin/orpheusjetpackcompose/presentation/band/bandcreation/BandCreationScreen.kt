package ru.potemkin.orpheusjetpackcompose.presentation.band.bandcreation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material3.Surface
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import ru.potemkin.orpheusjetpackcompose.R
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.presentation.search.FilterDialog
import ru.potemkin.orpheusjetpackcompose.presentation.search.MusicianListItem
import ru.potemkin.orpheusjetpackcompose.presentation.search.SearchScreenState
import ru.potemkin.orpheusjetpackcompose.presentation.search.SearchViewModel
import ru.potemkin.orpheusjetpackcompose.ui.theme.Black
import ru.potemkin.orpheusjetpackcompose.ui.theme.LightBlack
import ru.potemkin.orpheusjetpackcompose.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable

fun BandListScreen(
    onBackPressed: () -> Unit,
    onBandClickListener: (BandItem) -> Unit,
    onBandCreationClickListener: () -> Unit,
) {

    val viewModel: BandCreationViewModel = viewModel()
    val screenState = viewModel.screenState.observeAsState(BandCreationScreenState.Initial)
    when (val currentState = screenState.value) {
        is BandCreationScreenState.Bands -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text("Ваши группы",
                                color = White,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = { onBackPressed() }
                            ) {
                                Icon(
                                    Icons.Default.ArrowBack, contentDescription = null,
                                    tint = White
                                )
                            }
                        },
                        backgroundColor = Black,

                        )
                },
                content = {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(it)
                            .background(Black)
                    ) {
                        LazyColumn {
                            items(currentState.bands) { band ->
                                BandListItem(band = band, onItemClick = {
                                    onBandClickListener(band)
                                })
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                tint = White
                            )
                            Text(
                                text = "Создать группу",
                                fontWeight = FontWeight.Bold,
                                color = White,
                                modifier = Modifier.clickable {
                                    onBandCreationClickListener()
                                }
                            )
                        }
                    }
                })
        }

        BandCreationScreenState.Initial -> {}
        BandCreationScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.Black)
            }
        }
    }

}

@Composable
fun BandListItem(band: BandItem, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Аватар группы
        AsyncImage(
            model = band.photo.url,
            contentDescription = null,
            modifier = Modifier
                .size(86.dp)
                .padding(4.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            Text(text = band.name, fontWeight = FontWeight.Bold, color = White)
            Text(
                text = band.genre,
                color = White
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                band.members.forEach { member ->
                    AsyncImage(
                        model = member.profile_picture.url,
                        contentDescription = null,
                        modifier = Modifier
                            .size(36.dp)
                            .padding(4.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
    HorizontalDivider(
        modifier = Modifier.fillMaxWidth(),
        thickness = 3.dp,
        color = LightBlack
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BandCreationScreen(
    onBackPressed: () -> Unit,
) {
    var groupName by remember { mutableStateOf("") }
    var groupGenre by remember { mutableStateOf("") }
    var groupAvatar by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Create Group", color = White, fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onBackPressed() }
                    ) {
                        Icon(
                            Icons.Default.ArrowBack, contentDescription = null,
                            tint = White
                        )
                    }
                },
                backgroundColor = Black,
                actions = {
                    IconButton(
                        onClick = {
                            // TODO: Обработка создания группы
                        }
                    ) {
                        Icon(
                            Icons.Default.Check, contentDescription = null,
                            tint = White
                        )
                    }
                },

                )
        },
        content = {
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(bottomEndPercent = 10, bottomStartPercent = 10),
                color = Black
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = groupName,
                        onValueChange = { groupName = it },
                        label = { Text("Название") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Black,
                            unfocusedIndicatorColor = White,
                            focusedIndicatorColor = White,
                            focusedTextColor = White,
                            cursorColor = White
                        )
                    )
                    OutlinedTextField(
                        value = groupGenre,
                        onValueChange = { groupGenre = it },
                        label = { Text("Жанр") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Black,
                            unfocusedIndicatorColor = White,
                            focusedIndicatorColor = White,
                            focusedTextColor = White,
                            cursorColor = White
                        )
                    )

                    // TODO: Добавьте функциональность для загрузки аватарки группы

                    // Пример загрузки аватарки через кнопку
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                            .clickable { /* TODO: Обработка загрузки аватарки */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.CameraAlt,
                            contentDescription = null,
                            tint = Black,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
        }
    )
}