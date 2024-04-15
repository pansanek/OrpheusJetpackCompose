package ru.potemkin.orpheusjetpackcompose.domain.usecases.location_usecases

import kotlinx.coroutines.flow.StateFlow
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.LocationRepository
import javax.inject.Inject

class GetLocationListUseCase @Inject constructor(private val locationRepository: LocationRepository) {
    operator fun invoke(): StateFlow<List<LocationItem>> {
        return locationRepository.getLocationsList()
    }
}