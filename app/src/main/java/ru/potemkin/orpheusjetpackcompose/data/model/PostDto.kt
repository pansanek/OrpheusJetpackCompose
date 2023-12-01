package ru.potemkin.orpheusjetpackcompose.data.model

import com.google.gson.annotations.SerializedName

data class PostDto(
    @SerializedName("postId") val id: Int,
    @SerializedName("userId") val userId: Int,
    @SerializedName("text") val text: String,
    @SerializedName("date") val date: Long,
    @SerializedName("likes") val likes: LikesDto,
    @SerializedName("comments") val comments: CommentsDto,
    @SerializedName("attachments") val attachments: AttachmentDto
)