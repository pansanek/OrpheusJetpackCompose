package ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.UserRepository

class AddUserUseCase (private val userRepository: UserRepository) {
    fun addUserItem(userItem: UserItem){
        userRepository.addUserItem(userItem);
    }
}