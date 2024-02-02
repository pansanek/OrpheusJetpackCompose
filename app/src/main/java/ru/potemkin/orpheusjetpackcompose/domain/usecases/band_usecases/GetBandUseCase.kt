package ru.potemkin.orpheusjetpackcompose.domain.usecases.band_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.BandRepository
import javax.inject.Inject

class GetBandUseCase @Inject constructor(private val bandRepository: BandRepository) {
    fun getBandItem(bandItemId: String): BandItem {
        return bandRepository.getBandItem(bandItemId)
    }
}