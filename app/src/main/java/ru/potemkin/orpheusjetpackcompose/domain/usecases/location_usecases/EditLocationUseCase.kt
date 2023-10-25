package ru.potemkin.orpheusjetpackcompose.domain.usecases.location_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.LocationRepository

class EditLocationUseCase(private val locationRepository: LocationRepository) {
    fun editLocationItem(locationItem: LocationItem){
        locationRepository.editLocationItem(locationItem)
    }
}