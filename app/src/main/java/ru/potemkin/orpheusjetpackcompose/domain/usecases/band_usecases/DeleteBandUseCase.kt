package ru.potemkin.orpheusjetpackcompose.domain.usecases.band_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.BandRepository
import javax.inject.Inject

class DeleteBandUseCase @Inject constructor(private val bandRepository: BandRepository){
    suspend operator fun invoke(BandItem: BandItem){
        bandRepository.deleteBandItem(BandItem)
    }
}