package ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.MusicianItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.UserRepository
import javax.inject.Inject

class GetMusicianUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(userItem: UserItem): MusicianItem {
        return userRepository.getMusicianItem(userItem)
    }
}