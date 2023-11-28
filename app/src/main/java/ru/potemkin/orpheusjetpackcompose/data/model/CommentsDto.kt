package ru.potemkin.orpheusjetpackcompose.data.model

import com.google.gson.annotations.SerializedName

data class CommentsDto(
    @SerializedName("items") val comments: List<CommentDto>,
    @SerializedName("users") val profiles: List<UserDto>
)
