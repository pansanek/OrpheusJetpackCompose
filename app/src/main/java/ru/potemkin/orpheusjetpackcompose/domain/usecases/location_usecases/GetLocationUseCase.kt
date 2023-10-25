package ru.potemkin.orpheusjetpackcompose.domain.usecases.location_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.LocationRepository

class GetLocationUseCase (private val locationRepository: LocationRepository) {
    fun getLocationItem(locationItemId: Int): LocationItem {
        return locationRepository.getLocationItem(locationItemId)
    }
}