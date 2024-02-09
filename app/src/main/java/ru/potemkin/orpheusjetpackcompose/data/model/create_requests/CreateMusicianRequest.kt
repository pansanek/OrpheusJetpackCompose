package ru.potemkin.orpheusjetpackcompose.data.model.create_requests

import com.google.gson.annotations.SerializedName
import ru.potemkin.orpheusjetpackcompose.data.model.UserDto

data class CreateMusicianRequest(
    val user: UserDto,
    val genre: String,
    val instrument: String
)
