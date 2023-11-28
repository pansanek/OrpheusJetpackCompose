package ru.potemkin.orpheusjetpackcompose.data.model

import com.google.gson.annotations.SerializedName

data class CommentDto(
    @SerializedName("id") val id: Int,
    @SerializedName("from_id") val authorId: Int,
    @SerializedName("text") val text: String,
    @SerializedName("date") val date: Long
)