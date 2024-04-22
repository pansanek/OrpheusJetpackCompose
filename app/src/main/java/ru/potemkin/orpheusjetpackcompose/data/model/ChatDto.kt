package ru.potemkin.orpheusjetpackcompose.data.model

import com.google.gson.annotations.SerializedName

data class ChatDto(
    @SerializedName("id") val id: String,
    @SerializedName("users") val users: List<UserDto>,
    @SerializedName("last_message") val lastMessage: String,
    @SerializedName("picture") val picture: PhotoUrlDto,
    @SerializedName("name") val name: String
)