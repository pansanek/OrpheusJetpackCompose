package ru.potemkin.orpheusjetpackcompose.data.model

import com.google.gson.annotations.SerializedName

data class CommentDto(
    @SerializedName("commentId") val id: Int,
    @SerializedName("authorId") val authorId: Int,
    @SerializedName("text") val text: String,
    @SerializedName("date") val date: Long
)