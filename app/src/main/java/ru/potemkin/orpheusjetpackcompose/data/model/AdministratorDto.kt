package ru.potemkin.orpheusjetpackcompose.data.model

import com.google.gson.annotations.SerializedName

data class AdministratorDto(
    @SerializedName("id") val id: String,
    @SerializedName("user") val user: UserDto,
    @SerializedName("location_id") val locationId: String
)