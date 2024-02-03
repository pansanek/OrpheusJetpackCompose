package ru.potemkin.orpheusjetpackcompose.data.repositories

import ru.potemkin.orpheusjetpackcompose.data.mappers.LocationMapper
import ru.potemkin.orpheusjetpackcompose.data.mappers.MessageMapper
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(

) : LocationRepository {

    private val apiService = ApiFactory.appLocationApiService
    private val messageApiService = ApiFactory.appMessageApiService
    private val mapper = LocationMapper()
    private val messageMapper = MessageMapper()


    private val _locationItems = mutableListOf<LocationItem>()
    private val locationItems: List<LocationItem>
        get() = _locationItems.toList()

    private var nextFrom: String? = null
    override fun addLocationItem(locationItem: LocationItem) {
        _locationItems.add(locationItem)
    }

    suspend fun loadLocations(): List<LocationItem> {
        val startFrom = nextFrom

        if (startFrom == null && locationItems.isNotEmpty()) return locationItems

        val response = if (startFrom == null) {
            apiService.getAllLocations()
        } else {
            apiService.getAllLocations()
        }
        val locations = mapper.mapLocations(response)
        _locationItems.addAll(locations)
        return locationItems
    }

    override fun deleteLocationItem(locationItem: LocationItem) {
        _locationItems.remove(locationItem)
    }

    override fun editLocationItem(locationItem: LocationItem) {
        val oldElement = getLocationItem(locationItem.id)
        _locationItems.remove(oldElement)
        addLocationItem(locationItem)
    }

    override fun getLocationItem(locationId: String): LocationItem {
        return _locationItems.find {
            it.id == locationId
        } ?: throw java.lang.RuntimeException("Element with id $locationId not found")
    }

    override fun getLocationsList(): List<LocationItem> {
        return _locationItems.toList()
    }
}