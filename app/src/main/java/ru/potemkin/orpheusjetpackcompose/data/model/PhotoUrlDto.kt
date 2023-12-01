package ru.potemkin.orpheusjetpackcompose.data.model

import com.google.gson.annotations.SerializedName

data class PhotoUrlDto(
    @SerializedName("photoUrlId") val id: Int,
    @SerializedName("url") val url: String
)
