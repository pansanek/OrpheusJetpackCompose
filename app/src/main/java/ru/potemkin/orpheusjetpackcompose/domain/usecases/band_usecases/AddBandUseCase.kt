package ru.potemkin.orpheusjetpackcompose.domain.usecases.band_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.BandRepository
import javax.inject.Inject

class AddBandUseCase @Inject constructor(private val bandRepository: BandRepository) {
    operator fun invoke(bandItem: BandItem){
        bandRepository.addBandItem(bandItem);
    }
}