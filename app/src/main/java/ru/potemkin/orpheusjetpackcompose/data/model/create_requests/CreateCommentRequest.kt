package ru.potemkin.orpheusjetpackcompose.data.model.create_requests

import ru.potemkin.orpheusjetpackcompose.data.model.UserDto

data class CreateCommentRequest(
    val id:String,
    val postId:String,
    val user: UserDto,
    val text: String,
    val timestamp: String
)
