package ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.UserRepository
import javax.inject.Inject

class SetMyUserUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(userItem: UserItem) {
        return userRepository.setMyUser(userItem)
    }
}