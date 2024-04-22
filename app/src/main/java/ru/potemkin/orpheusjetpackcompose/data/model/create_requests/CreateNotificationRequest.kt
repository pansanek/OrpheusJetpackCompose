package ru.potemkin.orpheusjetpackcompose.data.model.create_requests

import com.google.gson.annotations.SerializedName
import ru.potemkin.orpheusjetpackcompose.data.model.BandDto
import ru.potemkin.orpheusjetpackcompose.data.model.PostDto
import ru.potemkin.orpheusjetpackcompose.data.model.UserDto

data class CreateNotificationRequest(
    val type: String,
    val title: String,
    val contentDescription: String,
    val date: String,
    val fromUser: UserDto,
    val toUser: UserDto,
    val postItem: PostDto?,
    val bandItem: BandDto?
)
