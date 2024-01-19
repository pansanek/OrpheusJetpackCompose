package ru.potemkin.orpheusjetpackcompose.domain.usecases.location_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.LocationRepository
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(private val locationRepository: LocationRepository) {
    fun getLocationItem(locationItemId: String): LocationItem {
        return locationRepository.getLocationItem(locationItemId)
    }
}