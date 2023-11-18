package ru.potemkin.orpheusjetpackcompose.domain.usecases.location_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.LocationRepository
import javax.inject.Inject

class AddLocationUseCase @Inject constructor(private val locationRepository: LocationRepository) {
    fun addLocationItem(locationItem: LocationItem){
        locationRepository.addLocationItem(locationItem);
    }
}