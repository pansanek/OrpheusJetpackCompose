package ru.potemkin.orpheusjetpackcompose.data.network

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.potemkin.orpheusjetpackcompose.data.model.ChatDto
import ru.potemkin.orpheusjetpackcompose.data.model.LocationDto

interface ChatApiService {

    @GET("/api/chats/")
    suspend fun getAllChats(): List<ChatDto>

    @POST("/api/chats/")
    suspend fun createChat(@Body requestBody: RequestBody): ChatDto

    @GET("/api/chats/{id}")
    suspend fun getChatById(@Path("id") id: String): ChatDto

    @PUT("/api/chats/{id}")
    suspend fun updateChat(
        @Path("id") id: String,
        @Body requestBody: RequestBody
    ): ChatDto
}