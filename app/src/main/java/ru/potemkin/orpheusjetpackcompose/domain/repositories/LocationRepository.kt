package ru.potemkin.orpheusjetpackcompose.domain.repositories

import kotlinx.coroutines.flow.StateFlow
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem

interface LocationRepository {
    suspend fun addLocationItem(locationItem: LocationItem)

    suspend fun deleteLocationItem(locationItem: LocationItem)

    suspend fun editLocationItem(locationItem: LocationItem)

    suspend fun getLocationItem(locationId: String): LocationItem

    fun getLocationsList(): StateFlow<List<LocationItem>>

    suspend fun getMyUserLocation(userId: String): LocationItem

    suspend fun getUserLocation(userId: String): LocationItem
}

