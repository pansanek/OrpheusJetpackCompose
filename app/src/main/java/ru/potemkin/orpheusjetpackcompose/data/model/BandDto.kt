package ru.potemkin.orpheusjetpackcompose.data.model

import com.google.gson.annotations.SerializedName

data class BandDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("members") val members: List<String>,
    @SerializedName("genre") val genre: String,
    @SerializedName("photo") val photo: PhotoUrlDto
)