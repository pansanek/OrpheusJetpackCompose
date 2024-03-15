package ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.UserRepository
import javax.inject.Inject

class GetMyUserUseCase @Inject constructor(private val userRepository: UserRepository) {
    fun getMyUser(): UserItem {
        return userRepository.getMyUser()
    }
}