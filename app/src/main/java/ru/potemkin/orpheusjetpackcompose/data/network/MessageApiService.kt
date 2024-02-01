package ru.potemkin.orpheusjetpackcompose.data.network

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.potemkin.orpheusjetpackcompose.data.model.MessageDto

interface MessageApiService {

    @GET("/api/messages/")
    suspend fun getAllMessages(): List<MessageDto>

    @GET("/api/messages/chat/{id}")
    suspend fun getChatMessages(@Path("id") id: String): List<MessageDto>
    @POST("/api/messages/")
    suspend fun createMessage(@Body requestBody: RequestBody): MessageDto

    @GET("/api/messages/{id}")
    suspend fun getMessageById(@Path("id") id: String): MessageDto
}