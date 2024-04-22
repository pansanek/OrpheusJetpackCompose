package ru.potemkin.orpheusjetpackcompose.data.model.create_requests

import ru.potemkin.orpheusjetpackcompose.data.model.UserDto

data class CreateCommentRequest(
    val user: UserDto,
    val post_id: String,
    val text: String,
    val timestamp: String
)
