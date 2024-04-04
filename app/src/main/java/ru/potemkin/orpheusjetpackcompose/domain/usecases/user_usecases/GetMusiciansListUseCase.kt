package ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.MusicianItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.UserRepository
import javax.inject.Inject

class GetMusiciansListUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(): List<MusicianItem>{
        return userRepository.getMusiciansList()
    }
}