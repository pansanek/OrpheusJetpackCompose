package ru.potemkin.orpheusjetpackcompose.data.model.create_requests

import ru.potemkin.orpheusjetpackcompose.data.model.UserDto

data class CreateBandRequest(
    val name: String,
    val member: UserDto,
    val genre: String
)
