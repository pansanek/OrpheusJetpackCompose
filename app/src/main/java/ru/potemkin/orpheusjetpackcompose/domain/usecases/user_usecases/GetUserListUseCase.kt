package ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases

import kotlinx.coroutines.flow.StateFlow
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.UserRepository
import javax.inject.Inject

class GetUserListUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(): StateFlow<List<UserItem>> {
        return userRepository.getUsersList()
    }
}