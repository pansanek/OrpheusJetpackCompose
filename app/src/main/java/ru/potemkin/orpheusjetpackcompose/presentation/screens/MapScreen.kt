package ru.potemkin.orpheusjetpackcompose.presentation.screens

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import ru.potemkin.orpheusjetpackcompose.presentation.components.BottomNavigationBar
import ru.potemkin.orpheusjetpackcompose.presentation.navigation.CHAT_SCREEN
import ru.potemkin.orpheusjetpackcompose.presentation.navigation.LOCATION_SCREEN
import ru.potemkin.orpheusjetpackcompose.presentation.navigation.USER_PROFILE_SCREEN

//import com.yandex.mapkit.MapKitFactory
//import com.yandex.mapkit.map.Map
//import com.yandex.mapkit.mapview.MapView

data class Location(
    val name: String,
    val description: String,
    val address: String,
    @DrawableRes val photo: Int,
    val type: String,
    val administratorId: Int
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    navHostController: NavHostController
) {

    val locations = remember {
        mutableStateListOf(
            Location(
                "Культ",
                "КУЛЬТ - настоящий храм творчества и оплот музыкальной КУЛЬТуры, созданный музыкантами для музыкантов.",
                "Электрозаводская улица, 21, Москва",
                ru.potemkin.orpheusjetpackcompose.R.drawable.location1,
                "Repbase",
                1

            )
        )
    }

// Create a mutable state for the selected location
    var selectedLocation by remember { mutableStateOf<Location?>(null) }

    // Create a function to show the details dialog
    fun showDetailsDialog(location: Location) {
        selectedLocation = location
    }

    // Create a function to dismiss the details dialog
    fun dismissDetailsDialog() {
        selectedLocation = null
    }
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navHostController = navHostController
            )
        },
        content = {
            Column {
                // Toolbar
                TopAppBar(
                    title = { Text(text = "Locations") }
                )

                // Location List
                LazyColumn {
                    items(locations) { location ->
                        LocationItem(location = location, onClick = { showDetailsDialog(location) })
                    }
                }

                // Details Dialog
                selectedLocation?.let { location ->
                    DetailsDialog(
                        navHostController,
                        location = location,
                        onDismiss = { dismissDetailsDialog() }
                    )
                }
            }
        })
}


@Composable
fun LocationItem(location: Location, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(location.photo),
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .background(Color.Gray)
                .align(Alignment.CenterVertically),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(text = location.name, style = MaterialTheme.typography.titleSmall)
    }
}

@Composable
fun DetailsDialog(navHostController: NavHostController,location: Location, onDismiss: () -> Unit) {
    val shape = MaterialTheme.shapes.medium

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface, shape = shape)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Close button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = { onDismiss() },
                        modifier = Modifier
                            .size(32.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Image(
                    painter = painterResource(location.photo),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(ru.potemkin.orpheusjetpackcompose.R.drawable.sample2),
                        contentDescription = "avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(onClick = { /* Handle button click, navigate to location page */ }) {
                        Text(text = "Написать администратору")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = location.name, style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = location.description, style = MaterialTheme.typography.titleMedium)

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { navHostController.navigate(LOCATION_SCREEN) }) {
                    Text(text = "Подробнее")
                }
            }
        }
    }
}
