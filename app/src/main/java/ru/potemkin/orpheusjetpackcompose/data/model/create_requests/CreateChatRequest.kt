package ru.potemkin.orpheusjetpackcompose.data.model.create_requests

import ru.potemkin.orpheusjetpackcompose.data.model.UserDto

data class CreateChatRequest(
    val user: UserDto,
    val secondUser:UserDto
)
