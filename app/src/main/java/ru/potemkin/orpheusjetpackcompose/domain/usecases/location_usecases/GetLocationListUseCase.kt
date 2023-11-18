package ru.potemkin.orpheusjetpackcompose.domain.usecases.location_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.LocationRepository
import javax.inject.Inject

class GetLocationListUseCase @Inject constructor(private val locationRepository: LocationRepository) {
    fun getLocationList(): List<LocationItem>{
        return locationRepository.getLocationsList()
    }
}