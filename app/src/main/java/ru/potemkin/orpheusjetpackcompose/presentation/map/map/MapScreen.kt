package ru.potemkin.orpheusjetpackcompose.presentation.map.map

import android.annotation.SuppressLint
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.transform.CircleCropTransformation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.maps.android.compose.CameraPositionState
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.presentation.main.getApplicationComponent


@Composable
fun GoogleMapComposable(
    locations: List<LocationItem>,
    cameraPositionState: CameraPositionState,
    onMarkerClick: (LocationItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val imageLoader = ImageLoader(context)

    val bitmapDescriptors = remember { mutableStateMapOf<String, BitmapDescriptor?>() }
    val imageBitmaps = remember { mutableStateMapOf<String, ImageBitmap?>() }
    LaunchedEffect(locations) {
        locations.forEach { location ->
            val request = ImageRequest.Builder(context)
                .data(location.profilePicture.url)
                .size(100) // Установите желаемый размер изображения
                .transformations(CircleCropTransformation()) // Примените трансформацию для создания круглого изображения
                .build()
            val result = (imageLoader.execute(request) as SuccessResult).drawable
            bitmapDescriptors[location.id] =
                BitmapDescriptorFactory.fromBitmap((result as BitmapDrawable).bitmap)

            val imageBitmap = (result as BitmapDrawable).bitmap.asImageBitmap()
            imageBitmaps[location.id] = imageBitmap
        }
    }
    var selectedLocation by remember { mutableStateOf<LocationItem?>(null) }

    // Показывать диалоговое окно, если selectedLocation не null
    if (selectedLocation != null) {
        selectedLocation?.let { location ->
            CustomAlertDialog(
                location = location,
                onDismiss = { selectedLocation = null },
                onConfirm = {
                    onMarkerClick(location)
                    selectedLocation = null
                })
        }
    }


    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        locations.forEach { location ->
            val position = LatLng(location.latitude, location.longitude)
            Marker(
                state = MarkerState(position = position),
                title = location.name,
                icon = bitmapDescriptors[location.id],
                onClick = {
                    selectedLocation = location
                    true // Indicate that the click has been handled
                }
            )
        }
    }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(
    paddingValues: PaddingValues,
    onLocationClickListener: (LocationItem) -> Unit,
) {
    val component = getApplicationComponent()
    val viewModel: MapViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.screenState.observeAsState(MapScreenState.Initial)
    val context = LocalContext.current


    val locationPermissionState = rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)
    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    LaunchedEffect(key1 = true) {
        // Запрос разрешения на доступ к геолокации
        locationPermissionState.launchPermissionRequest()

        // Проверка, предоставлено ли разрешение
        if (locationPermissionState.hasPermission) {
            try {
                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    location?.let {
                        userLocation = LatLng(it.latitude, it.longitude)
                    }
                }
            } catch (e: SecurityException) {
                // Обработка исключения, если разрешение было отклонено в последний момент
            }
        }
    }

    val cameraPositionState = rememberCameraPositionState {
        userLocation?.let {
            position = CameraPosition.fromLatLngZoom(it, 1f)
        }
    }
    when (val currentState = screenState.value) {
        is MapScreenState.Locations -> {
//
            GoogleMapComposable(
                locations = currentState.locations,
                cameraPositionState = cameraPositionState,
                onMarkerClick = onLocationClickListener,
                modifier = Modifier.padding(paddingValues)
            )
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
fun CustomAlertDialog(location: LocationItem, onDismiss: () -> Unit, onConfirm: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Отображение изображения
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(location.profilePicture.url)
                        .crossfade(true)
                        .build(),
                    contentDescription = location.name,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                // Заголовок
                Text(text = location.name, style = MaterialTheme.typography.titleLarge)
                // Текст
                Text(text = location.about, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(16.dp))
                // Кнопки действий
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        onClick = onDismiss,
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.Black)
                    ) {
                        Text("Отмена")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = onConfirm,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White
                        ), // Установка черного цвета фона
                    ) {
                        Text("Открыть")
                    }
                }
            }
        }
    }
}






