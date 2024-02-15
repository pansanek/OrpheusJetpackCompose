package ru.potemkin.orpheusjetpackcompose.data.model

import com.google.gson.annotations.SerializedName

data class PostDto(
    @SerializedName("post_id") val postId: String,
    @SerializedName("user_id") val creatorId: String,
    @SerializedName("caption") val caption: String,
    @SerializedName("timestamp") val timestamp: String,
    @SerializedName("likes") val likes: List<String>,
    @SerializedName("views") val views: Int,
    @SerializedName("comments") val comments: List<CommentDto>,
    @SerializedName("attachment") val attachment: PhotoUrlDto,
    @SerializedName("creator_type") val creatorType: String
)