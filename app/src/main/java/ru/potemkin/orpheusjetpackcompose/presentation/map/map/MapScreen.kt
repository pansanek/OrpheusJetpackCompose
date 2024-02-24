package ru.potemkin.orpheusjetpackcompose.presentation.map.map

import android.annotation.SuppressLint
import android.os.Bundle
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem


//@Composable
//fun MapScreen(
//
//){
//    val singapore = LatLng(1.35, 103.87)
//    val cameraPositionState = rememberCameraPositionState {
//        position = CameraPosition.fromLatLngZoom(singapore, 10f)
//    }
//    GoogleMap(
//        modifier = Modifier.fillMaxSize(),
//        cameraPositionState = cameraPositionState
//    ) {
//        Marker(
//            state = MarkerState(position = singapore),
//            title = "Singapore",
//            snippet = "Marker in Singapore"
//        )
//    }
//}

@Composable
fun GoogleMapComposable(locations: List<LocationItem>,
                        onMarkerClick: (LocationItem) -> Unit) {
    val context = LocalContext.current
    AndroidView(factory = { ctx ->
        MapView(ctx).apply {
            onCreate(Bundle())
            getMapAsync { googleMap ->
                googleMap.uiSettings.isZoomControlsEnabled = true
                locations.forEach { location ->
                    val position = LatLng(location.latitude, location.longitude)
                    googleMap.addMarker(MarkerOptions().position(position).title(location.name))
                }
                if (locations.isNotEmpty()) {
                    val firstLocation = LatLng(locations.first().latitude, locations.first().longitude)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLocation, 10f))
                }
                googleMap.setOnMarkerClickListener { marker ->
                    val locationItem = marker.tag as? LocationItem
                    locationItem?.let {
                        onMarkerClick(it) // Вызов колбэка с LocationItem как аргументом
                    }
                    true // Возвращает true, чтобы указать, что мы обработали событие клика
                }
            }
        }
    }, modifier = Modifier.fillMaxSize())
}

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
//            Column {
//                // Toolbar
//                TopAppBar(
//                    title = { Text(text = "Locations") }
//                )
//
//                // Location List
//                LazyColumn(
//                modifier = Modifier.padding(bottom = 15.dp, top = 30.dp)
//                )  {
//                    items(currentState.locations, key = { it.id }) {
//                        LocationItem(location = it, onClick = { onLocationClickListener(it) })
//                    }
//                }
//            }

            GoogleMapComposable(locations = currentState.locations,
                onMarkerClick = onLocationClickListener)
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






