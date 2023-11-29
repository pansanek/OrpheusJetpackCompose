package ru.potemkin.orpheusjetpackcompose.data.model

import com.google.gson.annotations.SerializedName

data class LikesDto(
    @SerializedName("likesId") val id: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("userLikes") val userLikes: Int
)