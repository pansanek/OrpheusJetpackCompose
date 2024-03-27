package ru.potemkin.orpheusjetpackcompose.domain.usecases.location_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.LocationRepository
import javax.inject.Inject

class GetUserLocationUseCase @Inject constructor(private val locationRepository: LocationRepository) {
    operator fun invoke(userId: String): LocationItem {
        return locationRepository.getUserLocation(userId)
    }
}