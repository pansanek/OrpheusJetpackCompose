package ru.potemkin.orpheusjetpackcompose.presentation.map.map

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem

//import com.yandex.mapkit.MapKitFactory
//import com.yandex.mapkit.map.Map
//import com.yandex.mapkit.mapview.MapView


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    paddingValues: PaddingValues,
    onLocationClickListener: (LocationItem) -> Unit,
) {

    val viewModel: MapViewModel = viewModel()
    val screenState = viewModel.screenState.observeAsState(MapScreenState.Initial)
    when (val currentState = screenState.value) {
        is MapScreenState.Locations -> {
            Column {
                // Toolbar
                TopAppBar(
                    title = { Text(text = "Locations") }
                )

                // Location List
                LazyColumn(
                modifier = Modifier.padding(bottom = 15.dp, top = 30.dp)
                )  {
                    items(currentState.locations, key = { it.id }) {
                        LocationItem(location = it, onClick = { onLocationClickListener(it) })
                    }
                }
            }
        }

        MapScreenState.Initial -> {}
        MapScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.Black)
            }
        }
    }

}


@Composable
fun LocationItem(location: LocationItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick)
    ) {
        AsyncImage(
            model = location.profilePicture.url,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .clickable {
                    onClick()
                },
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(text = location.name, style = MaterialTheme.typography.titleSmall)
    }
}






