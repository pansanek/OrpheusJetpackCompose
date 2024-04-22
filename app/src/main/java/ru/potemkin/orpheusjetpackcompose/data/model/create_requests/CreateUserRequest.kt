package ru.potemkin.orpheusjetpackcompose.data.model.create_requests

import com.google.gson.annotations.SerializedName
import ru.potemkin.orpheusjetpackcompose.data.model.PhotoUrlDto
import ru.potemkin.orpheusjetpackcompose.data.model.UserSettingsDto

data class CreateUserRequest(
    val login: String,
    val name: String,
    val password: String,
    val email: String,
    val about: String,
    val userType: String,
    val profile_picture: PhotoUrlDto,
    val background_picture: PhotoUrlDto,
    val settings: UserSettingsDto
)
