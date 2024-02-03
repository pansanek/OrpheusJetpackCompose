package ru.potemkin.orpheusjetpackcompose.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MusicianItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem


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
    var isFilterExpanded by remember { mutableStateOf(false) }

    val viewModel: SearchViewModel = viewModel()
    val screenState = viewModel.screenState.observeAsState(SearchScreenState.Initial)
    when (val currentState = screenState.value) {
        is SearchScreenState.Finds -> {
            Column {
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            IconButton(onClick = { onBackPressed() }) {
                                Icon(Icons.Outlined.ArrowBack, contentDescription = "Back")
                            }
                            OutlinedTextField(
                                value = searchText,
                                onValueChange = { searchText = it },
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                                    .padding(8.dp),
                                label = { Text("Search") },
                                trailingIcon = {
                                    IconButton(onClick = { /* Handle search icon click */ }) {
                                        Icon(Icons.Outlined.Search, contentDescription = "Search")
                                    }
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                            )
                            IconButton(onClick = { isFilterExpanded = true }) {
                                Icon(Icons.Outlined.Settings, contentDescription = "Filter")
                            }
                        }
                    }
                )

                if (isFilterExpanded) {
                    FilterDialog(
                        onDismiss = { isFilterExpanded = false },
                        onGenreSelected = { selectedGenre = it },
                        onInstrumentSelected = { selectedInstrument = it }
                    )
                }

                LazyColumn {
                    items(currentState.musicians) { musician ->
                        MusicianListItem(musician = musician) {
                            onUserClickListener(musician.user)
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
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column {
            Text(text = musician.user.name, style = MaterialTheme.typography.titleLarge)
            Text(text = "Genre: ${musician.genre}", style = MaterialTheme.typography.titleSmall)
            Text(text = "Instrument: ${musician.instrument}", style = MaterialTheme.typography.titleSmall)
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
                    },text=
                    {Text("Pop")})
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
                    },text={
                        Text("Guitar")
                    })
                    DropdownMenuItem(onClick = {
                        selectedInstrument = "Piano"
                        onInstrumentSelected("Piano")
                    },text={
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