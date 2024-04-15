package ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases

import kotlinx.coroutines.flow.StateFlow
import ru.potemkin.orpheusjetpackcompose.domain.entities.MusicianItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.UserRepository
import javax.inject.Inject

class GetMusiciansListUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(): StateFlow<List<MusicianItem>> {
        return userRepository.getMusiciansList()
    }
}