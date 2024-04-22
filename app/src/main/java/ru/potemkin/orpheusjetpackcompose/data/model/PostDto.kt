package ru.potemkin.orpheusjetpackcompose.data.model

import com.google.gson.annotations.SerializedName

data class PostDto(
    @SerializedName("post_id") val postId: String,
    @SerializedName("creatorId") val creatorId: String,
    @SerializedName("creatorName") val creatorName: String,
    @SerializedName("creatorPicture") val creatorPicture: PhotoUrlDto,
    @SerializedName("text") val text: String,
    @SerializedName("date") val date: String,
    @SerializedName("likes") val likes: List<String>,
    @SerializedName("comments") val comments: List<CommentDto>,
    @SerializedName("attachment") val attachment: PhotoUrlDto,
    @SerializedName("creator_type") val creatorType: String
)