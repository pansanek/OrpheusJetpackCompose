package ru.potemkin.orpheusjetpackcompose.data.model.create_requests

import com.google.gson.annotations.SerializedName
import ru.potemkin.orpheusjetpackcompose.data.model.UserDto

data class CreateAdministratorRequest(
    val user: UserDto,
    val locationId: String
)
