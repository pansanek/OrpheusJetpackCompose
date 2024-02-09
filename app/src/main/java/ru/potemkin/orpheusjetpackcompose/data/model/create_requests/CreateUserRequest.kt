package ru.potemkin.orpheusjetpackcompose.data.model.create_requests

import com.google.gson.annotations.SerializedName

data class CreateUserRequest(
    val login: String,
    val name: String,
    val password: String,
    val email: String,
    val about: String,
    val userType: String,
)
