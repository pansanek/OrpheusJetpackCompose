package ru.potemkin.orpheusjetpackcompose.domain.usecases.musician_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.MusicianItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.MusicianRepository
import javax.inject.Inject

class GetMusicianListUseCase @Inject constructor(private val musicianRepository: MusicianRepository) {
    fun getMusicianList(): List<MusicianItem>{
        return musicianRepository.getMusiciansList()
    }
}