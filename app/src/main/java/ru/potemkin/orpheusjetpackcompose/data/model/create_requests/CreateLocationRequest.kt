package ru.potemkin.orpheusjetpackcompose.data.model.create_requests

import com.google.gson.annotations.SerializedName
import ru.potemkin.orpheusjetpackcompose.data.model.AdministratorDto
import ru.potemkin.orpheusjetpackcompose.data.model.UserDto

data class CreateLocationRequest(
    val user: UserDto,
    val name: String,
    val address: String,
    val about: String,
)
