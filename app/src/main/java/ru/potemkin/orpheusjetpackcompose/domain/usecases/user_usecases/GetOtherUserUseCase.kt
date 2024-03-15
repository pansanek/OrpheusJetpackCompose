package ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.UserRepository
import javax.inject.Inject

class GetOtherUserUseCase @Inject constructor(private val userRepository: UserRepository) {
    fun getOtherUserItem(userItemId: String): UserItem {
        return userRepository.getOtherUser(userItemId)
    }
}