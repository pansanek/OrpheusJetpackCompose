package ru.potemkin.orpheusjetpackcompose.data.repositories

import android.app.Application
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(

): LocationRepository {

    private val locationList= mutableListOf<LocationItem>()

    private var autoIncrementId =0
    override fun addLocationItem(locationItem: LocationItem) {
        locationList.add(locationItem)
    }

    override fun deleteLocationItem(locationItem: LocationItem) {
        locationList.remove(locationItem)
    }

    override fun editLocationItem(locationItem: LocationItem) {
        val oldElement = getLocationItem(locationItem.id)
        locationList.remove(oldElement)
        addLocationItem(locationItem)
    }

    override fun getLocationItem(locationId: String): LocationItem {
        return locationList.find {
            it.id == locationId
        } ?: throw java.lang.RuntimeException("Element with id $locationId not found")
    }

    override fun getLocationsList(): List<LocationItem> {
        return locationList.toList()
    }
}