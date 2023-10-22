package ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.UserRepository

class GetUserUseCase (private val userRepository: UserRepository) {
    fun getUserItem(userItemId: Int): UserItem {
        return userRepository.getUserItem(userItemId)
    }
}