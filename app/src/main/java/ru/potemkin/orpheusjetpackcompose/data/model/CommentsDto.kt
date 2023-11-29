package ru.potemkin.orpheusjetpackcompose.data.model

import com.google.gson.annotations.SerializedName

data class CommentsDto(
    @SerializedName("commentsId") val id: Int,
    @SerializedName("comments") val comments: List<CommentDto>,
    @SerializedName("profiles") val profiles: List<UserDto>
)
