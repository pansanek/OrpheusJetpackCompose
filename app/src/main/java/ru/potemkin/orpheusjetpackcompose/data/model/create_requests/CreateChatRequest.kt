package ru.potemkin.orpheusjetpackcompose.data.model.create_requests

import com.google.gson.annotations.SerializedName
import ru.potemkin.orpheusjetpackcompose.data.model.PhotoUrlDto
import ru.potemkin.orpheusjetpackcompose.data.model.UserDto

data class CreateChatRequest(
    val users: List<UserDto>,
     val lastMessage: String,
    val picture: PhotoUrlDto,
    val name: String
)
