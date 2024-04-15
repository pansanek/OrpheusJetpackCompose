package ru.potemkin.orpheusjetpackcompose.domain.usecases.band_usecases

import kotlinx.coroutines.flow.StateFlow
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.BandRepository
import javax.inject.Inject

class GetMyUserBandsUseCase @Inject constructor(private val bandRepository: BandRepository) {
    operator fun invoke(userId: String): StateFlow<List<BandItem>> {
        return bandRepository.getMyUserBands(userId)
    }
}