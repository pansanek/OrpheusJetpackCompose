package ru.potemkin.orpheusjetpackcompose.data.network

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.potemkin.orpheusjetpackcompose.data.model.CommentDto
import ru.potemkin.orpheusjetpackcompose.data.model.LocationDto

interface CommentApiService {

    @GET("/api/comments/")
    suspend fun getAllComments(): List<CommentDto>

    @POST("/api/comments")
    suspend fun createComment(@Body requestBody: RequestBody): CommentDto

    @GET("/api/comments/{id}")
    suspend fun getCommentById(@Path("id") id: String): CommentDto

    @PUT("/api/comments/{id}")
    suspend fun updateComment(
        @Path("id") id: String,
        @Body requestBody: RequestBody
    ): CommentDto
}