package ru.potemkin.orpheusjetpackcompose.data.model.create_requests

import ru.potemkin.orpheusjetpackcompose.data.model.PhotoUrlDto
import ru.potemkin.orpheusjetpackcompose.data.model.UserDto

data class CreateLocationRequest(
    val admin: UserDto,
    val name: String,
    val address: String,
    val about: String,
    val profile_picture:PhotoUrlDto
)
