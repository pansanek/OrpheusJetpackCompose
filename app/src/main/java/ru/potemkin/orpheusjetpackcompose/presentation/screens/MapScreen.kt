package ru.potemkin.orpheusjetpackcompose.presentation.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView

//@Composable
//fun MapScreen() {
//    val context = LocalContext.current
//    var map: Map? by remember { mutableStateOf(null) }
//
//    AndroidView(
//        modifier = Modifier.fillMaxSize(),
//        factory = { context ->
//            val mapView = MapView(context)
//            mapView.onStart()
//            mapView.map.move(
//                com.yandex.mapkit.geometry.Point(55.75, 37.62),
//                Map.Animation.NONE,
//                null
//            )
//            map = mapView.map
//            mapView
//        },
//        update = { mapView ->
//            // Update the map as needed
//        }
//    )
//}
