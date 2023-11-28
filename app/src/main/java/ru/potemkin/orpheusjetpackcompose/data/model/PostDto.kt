package ru.potemkin.orpheusjetpackcompose.data.model

import com.google.gson.annotations.SerializedName

data class PostDto(
    @SerializedName("id") val id: Int,
    @SerializedName("source_id") val userId: Int,
    @SerializedName("text") val text: String,
    @SerializedName("date") val date: Long,
    @SerializedName("likes") val likes: LikesDto,
    @SerializedName("comments") val comments: CommentsDto,
    @SerializedName("attachments") val attachments: List<AttachmentDto>?
)