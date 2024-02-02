package ru.potemkin.orpheusjetpackcompose.data.model

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id") val id: String,
    @SerializedName("login") val login: String,
    @SerializedName("name") val name: String,
    @SerializedName("password") val password: String,
    @SerializedName("email") val email: String,
    @SerializedName("about") val about: String,
    @SerializedName("user_type") val user_type: String,
    @SerializedName("profile_picture") val profile_picture: PhotoUrlDto,
    @SerializedName("background_picture") val background_picture: PhotoUrlDto,
    @SerializedName("settings") val settings: UserSettingsDto
)
