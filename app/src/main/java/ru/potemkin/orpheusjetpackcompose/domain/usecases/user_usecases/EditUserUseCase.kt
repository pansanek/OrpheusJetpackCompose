package ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.UserRepository

class EditUserUseCase (private val userRepository: UserRepository) {
    fun editUserItem(userItem: UserItem){
        userRepository.editUserItem(userItem)
    }
}