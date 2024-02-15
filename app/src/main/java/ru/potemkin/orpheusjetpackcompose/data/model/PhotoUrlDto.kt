package ru.potemkin.orpheusjetpackcompose.data.model

import com.google.gson.annotations.SerializedName

data class PhotoUrlDto(
    @SerializedName("id") val id: String,
    @SerializedName("url") val url: String
)
