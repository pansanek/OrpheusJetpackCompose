package ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.UserRepository

class GetUserListUseCase (private val userRepository: UserRepository) {
    fun getUserList(): List<UserItem>{
        return userRepository.getUsersList()
    }
}