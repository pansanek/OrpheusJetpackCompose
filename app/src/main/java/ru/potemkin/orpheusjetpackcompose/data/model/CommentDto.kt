package ru.potemkin.orpheusjetpackcompose.data.model

import com.google.gson.annotations.SerializedName

data class CommentDto(
    @SerializedName("id") val id: String,
    @SerializedName("post_id") val postId: String,
    @SerializedName("user") val user: UserDto,
    @SerializedName("text") val text: String,
    @SerializedName("timestamp") val timestamp: String
)
