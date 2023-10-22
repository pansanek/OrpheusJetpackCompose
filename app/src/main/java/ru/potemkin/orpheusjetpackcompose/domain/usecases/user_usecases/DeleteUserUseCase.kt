package ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.UserRepository

class DeleteUserUseCase (private val userRepository: UserRepository){
    fun deleteUserItem(UserItem: UserItem){
        userRepository.deleteUserItem(UserItem)
    }
}