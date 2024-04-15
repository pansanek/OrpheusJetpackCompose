package ru.potemkin.orpheusjetpackcompose.domain.repositories

import kotlinx.coroutines.flow.StateFlow
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem

interface LocationRepository {
    suspend fun addLocationItem(locationItem: LocationItem)

    suspend fun deleteLocationItem(locationItem: LocationItem)

    suspend fun editLocationItem(locationItem: LocationItem)

    fun getLocationItem(locationId: String): LocationItem

    fun getLocationsList(): StateFlow<List<LocationItem>>

    fun getMyUserLocation(userId: String): LocationItem

    fun getUserLocation(userId: String): LocationItem
}

