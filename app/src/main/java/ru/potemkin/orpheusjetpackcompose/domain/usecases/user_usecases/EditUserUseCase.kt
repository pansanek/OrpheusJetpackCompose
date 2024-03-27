package ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.UserRepository
import javax.inject.Inject

class EditUserUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(userItem: UserItem){
        userRepository.editUserItem(userItem)
    }
}