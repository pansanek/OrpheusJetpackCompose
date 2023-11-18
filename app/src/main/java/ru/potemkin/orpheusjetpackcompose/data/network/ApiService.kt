package ru.potemkin.orpheusjetpackcompose.data.network

import retrofit2.http.GET
import ru.potemkin.orpheusjetpackcompose.data.model.UserDto

interface ApiService {

    @GET("users/all-users")
    suspend fun loadAllUsers(): UserDto
}