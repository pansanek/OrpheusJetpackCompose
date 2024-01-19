package ru.potemkin.orpheusjetpackcompose.domain.repositories

import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem

interface LocationRepository {
    fun addLocationItem(locationItem: LocationItem)

    fun deleteLocationItem(locationItem: LocationItem)

    fun editLocationItem(locationItem: LocationItem)

    fun getLocationItem(locationId: String): LocationItem

    fun getLocationsList(): List<LocationItem>
}