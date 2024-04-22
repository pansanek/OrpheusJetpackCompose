package ru.potemkin.orpheusjetpackcompose.data.model.create_requests

import com.google.gson.annotations.SerializedName
import ru.potemkin.orpheusjetpackcompose.data.model.UserDto

data class CreateMessageRequest(
    val chat_id: String,
    val from_user: UserDto,
    val timestamp: String,
    val content: String,
)
