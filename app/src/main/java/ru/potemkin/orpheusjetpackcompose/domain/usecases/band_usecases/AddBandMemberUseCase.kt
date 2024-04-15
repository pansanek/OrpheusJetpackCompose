package ru.potemkin.orpheusjetpackcompose.domain.usecases.band_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.BandRepository
import javax.inject.Inject

class AddBandMemberUseCase @Inject constructor(private val bandRepository: BandRepository) {
    suspend operator fun invoke(bandItem: BandItem, myUser:UserItem){
        bandRepository.addBandMemberItem(bandItem,myUser);
    }
}