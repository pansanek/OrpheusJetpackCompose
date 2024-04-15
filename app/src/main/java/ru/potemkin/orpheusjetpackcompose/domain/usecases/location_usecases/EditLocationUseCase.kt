package ru.potemkin.orpheusjetpackcompose.domain.usecases.location_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.LocationRepository
import javax.inject.Inject

class EditLocationUseCase @Inject constructor(private val locationRepository: LocationRepository) {
    suspend operator fun invoke(locationItem: LocationItem){
        locationRepository.editLocationItem(locationItem)
    }
}