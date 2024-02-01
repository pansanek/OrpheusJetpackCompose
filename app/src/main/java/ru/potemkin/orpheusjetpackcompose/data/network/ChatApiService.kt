package ru.potemkin.orpheusjetpackcompose.data.network

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.potemkin.orpheusjetpackcompose.data.model.ChatDto

interface ChatApiService {

    @GET("/api/chats/")
    suspend fun getAllChats(): List<ChatDto>

    @POST("/api/chats/")
    suspend fun createChat(@Body requestBody: RequestBody): ChatDto

    @GET("/api/chats/{id}")
    suspend fun getChatById(@Path("id") id: String): ChatDto
}