package ru.potemkin.orpheusjetpackcompose.domain.repositories

import kotlinx.coroutines.flow.StateFlow
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import java.io.File

interface LocationRepository {
    suspend fun addLocationItem(locationItem: LocationItem)

    suspend fun deleteLocationItem(locationItem: LocationItem)

    suspend fun editLocationItem(locationItem: LocationItem)

    fun getLocationItem(locationId: String): LocationItem

    fun getLocationsList(): StateFlow<List<LocationItem>>

    suspend fun uploadPhoto(file: File, mimeType: String):String
    //fun getMyUserLocation(userId: String): LocationItem

    //fun getUserLocation(userId: String): LocationItem
}

