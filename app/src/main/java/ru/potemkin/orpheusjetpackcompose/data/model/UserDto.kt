package ru.potemkin.orpheusjetpackcompose.data.model

import com.google.gson.annotations.SerializedName

data class UserDto (
    @SerializedName("usersId") val id: Int,
    @SerializedName("password") val password: String,
    @SerializedName("name") val name: String,
    @SerializedName("login") val login: String,
    @SerializedName("usersType") val type: String
)
