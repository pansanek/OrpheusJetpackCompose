package ru.potemkin.orpheusjetpackcompose.domain.usecases.musician_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.MusicianItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.MusicianRepository
import javax.inject.Inject

class AddMusicianUseCase @Inject constructor(private val musicianRepository: MusicianRepository) {
    fun addMusicianItem(musicianItem: MusicianItem){
        musicianRepository.addMusicianItem(musicianItem);
    }
}