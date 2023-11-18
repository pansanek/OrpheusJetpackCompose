package ru.potemkin.orpheusjetpackcompose.data.mappers

import ru.potemkin.orpheusjetpackcompose.data.model.UserDto
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem

class UsersMapper {

    fun mapUsers(userDto: UserDto):List<UserItem>{
        val result = mutableListOf<UserItem>()
        TODO()
    }
}