package ru.potemkin.orpheusjetpackcompose.presentation.search

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.SwitchColors
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MusicianItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.presentation.components.SpacerWidth
import ru.potemkin.orpheusjetpackcompose.presentation.main.getApplicationComponent
import ru.potemkin.orpheusjetpackcompose.ui.theme.Black
import ru.potemkin.orpheusjetpackcompose.ui.theme.Grey
import ru.potemkin.orpheusjetpackcompose.ui.theme.LightBlack
import ru.potemkin.orpheusjetpackcompose.ui.theme.White


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    paddingValues: PaddingValues,
    onUserClickListener: (UserItem) -> Unit,
    onBandClickListener: (BandItem) -> Unit,
    onBackPressed: () -> Unit,
) {

    var searchText by remember { mutableStateOf("") }
    var selectedGenre by remember { mutableStateOf("") }
    var selectedInstrument by remember { mutableStateOf("") }

    var isSearchingUsers by remember { mutableStateOf(true) }

    val component = getApplicationComponent()
    val viewModel: SearchViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.screenState.collectAsState(SearchScreenState.Initial)
    when (val currentState = screenState.value) {
        is SearchScreenState.Finds -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Поиск", color = White) },
                        navigationIcon = {
                            IconButton(onClick = { onBackPressed() }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Back",
                                tint = White)
                            }
                        },
                        actions = {
                            Switch(
                                checked = isSearchingUsers,
                                onCheckedChange = { isSearchingUsers = it },
                                colors = SwitchColors(
                                    checkedThumbColor = White,
                                    checkedTrackColor = Black,
                                    checkedBorderColor = White,
                                    checkedIconColor = Black,
                                    uncheckedThumbColor = White,
                                    uncheckedTrackColor = Black,
                                    uncheckedBorderColor = White,
                                    uncheckedIconColor = Black,
                                    disabledCheckedThumbColor = Black,
                                    disabledCheckedTrackColor = Black,
                                    disabledCheckedBorderColor = Black,
                                    disabledCheckedIconColor = Black,
                                    disabledUncheckedThumbColor = Black,
                                    disabledUncheckedTrackColor = Black,
                                    disabledUncheckedBorderColor = Black,
                                    disabledUncheckedIconColor = Black
                                ),
                                thumbContent = {
                                    Icon(
                                        imageVector = if (isSearchingUsers)
                                            Icons.Default.Person
                                        else Icons.Default.Groups,
                                        contentDescription = null,
                                        modifier = Modifier.size(SwitchDefaults.IconSize)
                                    )
                                },
                            )
                        },
                        backgroundColor = Black

                    )
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .background(Black)
                ) {
                    TextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        label = { Text("Искать") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Black,
                            unfocusedIndicatorColor = White,
                            focusedIndicatorColor = White,
                            focusedTextColor = White,
                            cursorColor = White
                        )
                    )
                    LazyColumn(
                        contentPadding = PaddingValues(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        ),
                        modifier = Modifier.background(Black)
                    ) {
                        if (isSearchingUsers) {
                            items(currentState.musicians.filter {
                                it.user.name.contains(searchText, ignoreCase = true)
                            }
                            ) { result ->
                                MusicianListItem(result, { onUserClickListener(result.user) })
                            }
                        } else{
                            items(currentState.bands.filter {
                                it.name.contains(searchText, ignoreCase = true)
                            }
                            ) { result ->
                                BandListItem(result, { onBandClickListener(result) })
                            }
                        }
                    }
                }
            }
        }


        SearchScreenState.Initial -> {}
        SearchScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.Black)
            }
        }
    }
}

@Composable
fun MusicianListItem(musician: MusicianItem, onItemClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
            .padding(16.dp)
            .background(Black)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                AsyncImage(
                    model = musician.user.profile_picture.url,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
                SpacerWidth()
                Column {
                    Text(
                        text = musician.user.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = White
                    )
                    Text(
                        text = "Genre: ${musician.genre}",
                        style = MaterialTheme.typography.titleSmall,
                        color = White
                    )
                    Text(
                        text = "Instrument: ${musician.instrument}",
                        style = MaterialTheme.typography.titleSmall,
                        color = White
                    )
                }
            }
            HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = Grey)
        }
    }
}
@Composable
fun BandListItem(band: BandItem, onItemClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
            .padding(16.dp)
            .background(Black)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                AsyncImage(
                    model = band.photo.url,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
                SpacerWidth()
                Column {
                    Text(
                        text = band.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = White
                    )
                    Text(
                        text = "Genre: ${band.genre}",
                        style = MaterialTheme.typography.titleSmall,
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
            HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = Grey)
        }
    }
}
@Composable
fun FilterDialog(
    onDismiss: () -> Unit,
    onGenreSelected: (String) -> Unit,
    onInstrumentSelected: (String) -> Unit
) {
    var selectedGenre by remember { mutableStateOf("") }
    var selectedInstrument by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Filter") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Filter by Genre:")
                Spacer(modifier = Modifier.height(8.dp))
                DropdownMenu(
                    expanded = selectedGenre.isNotEmpty(),
                    onDismissRequest = { /* Handle dismiss */ }
                ) {
                    // Add genre options here
                    DropdownMenuItem(onClick = {
                        selectedGenre = "Rock"
                        onGenreSelected("Rock")
                    },
                        text = {
                            Text("Rock")
                        })
                    DropdownMenuItem(onClick = {
                        selectedGenre = "Pop"
                        onGenreSelected("Pop")
                    }, text =
                    { Text("Pop") })
                    // Add more genre options as needed
                }
                Spacer(modifier = Modifier.height(16.dp))

                Text("Filter by Instrument:")
                Spacer(modifier = Modifier.height(8.dp))
                DropdownMenu(
                    expanded = selectedInstrument.isNotEmpty(),
                    onDismissRequest = { /* Handle dismiss */ }
                ) {
                    // Add instrument options here
                    DropdownMenuItem(onClick = {
                        selectedInstrument = "Guitar"
                        onInstrumentSelected("Guitar")
                    }, text = {
                        Text("Guitar")
                    })
                    DropdownMenuItem(onClick = {
                        selectedInstrument = "Piano"
                        onInstrumentSelected("Piano")
                    }, text = {
                        Text("Piano")
                    })
                    // Add more instrument options as needed
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text("Apply")
            }
        },
        dismissButton = {
            Button(onClick = {
                selectedGenre = ""
                selectedInstrument = ""
                onDismiss()
            }) {
                Text("Cancel")
            }
        }
    )
}


//@Preview(showBackground = true)
//@Composable
//fun SearchPreview() {
//    OrpheusJetpackComposeTheme {
//        SearchScreen()
//    }
//}