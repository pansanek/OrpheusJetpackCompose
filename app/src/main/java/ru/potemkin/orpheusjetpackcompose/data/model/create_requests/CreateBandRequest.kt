package ru.potemkin.orpheusjetpackcompose.data.model.create_requests

import com.google.gson.annotations.SerializedName
import ru.potemkin.orpheusjetpackcompose.data.model.PhotoUrlDto
import ru.potemkin.orpheusjetpackcompose.data.model.UserDto

data class CreateBandRequest(
    val name: String,
    val members: List<UserDto>,
    val genre: String,
    val photo: PhotoUrlDto
)
