package ru.potemkin.orpheusjetpackcompose.data.model

import com.google.gson.annotations.SerializedName

data class MessageDto (
    @SerializedName("id") val id: String,
    @SerializedName("chat_id") val chatId: String,
    @SerializedName("from_user") val fromUser: UserDto,
    @SerializedName("timestamp") val timestamp: String,
    @SerializedName("content") val content: String,
)