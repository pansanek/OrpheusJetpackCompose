package ru.potemkin.orpheusjetpackcompose.data.model

import com.google.gson.annotations.SerializedName

data class LocationDto(
    @SerializedName("id") val id: String,
    @SerializedName("admin") val admin: UserDto,
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: String,
    @SerializedName("about") val about: String,
    @SerializedName("profile_picture") val profilePicture: PhotoUrlDto
)