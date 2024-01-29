package ru.potemkin.orpheusjetpackcompose.data.model

import com.google.gson.annotations.SerializedName

data class CommentDto(
    @SerializedName("comment_id") val id: String,
    @SerializedName("post") val post: PostDto,
    @SerializedName("user") val user: UserDto,
    @SerializedName("text") val text: String,
    @SerializedName("timestamp") val timestamp: String
)
