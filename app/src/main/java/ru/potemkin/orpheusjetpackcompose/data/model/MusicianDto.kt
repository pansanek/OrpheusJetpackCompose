package ru.potemkin.orpheusjetpackcompose.data.model

import com.google.gson.annotations.SerializedName

data class MusicianDto(
    @SerializedName("id") val id: String,
    @SerializedName("user") val user: UserDto,
    @SerializedName("genre") val genre: String,
    @SerializedName("instrument") val instrument: String
)
