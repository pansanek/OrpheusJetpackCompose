package ru.potemkin.orpheusjetpackcompose.data.network

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.potemkin.orpheusjetpackcompose.data.model.PostDto
import ru.potemkin.orpheusjetpackcompose.data.model.UserDto

interface UserApiService {

    @GET("/api/users/")
    suspend fun getAllUsers(): List<UserDto>

    @POST("/api/users")
    suspend fun createUser(@Body requestBody: RequestBody): UserDto

    @GET("/api/users/{id}")
    suspend fun getUserById(@Path("id") id: String): UserDto

    @POST("/api/users/auth")
    suspend fun authorize(@Body requestBody: RequestBody): String

    @PUT("/api/users/{id}")
    suspend fun updateUser(
        @Path("id") id: String,
        @Body requestBody: RequestBody
    ): UserDto
}