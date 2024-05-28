package ru.potemkin.orpheusjetpackcompose.data.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import ru.potemkin.orpheusjetpackcompose.data.model.NotificationDto
import ru.potemkin.orpheusjetpackcompose.data.model.PostDto
import ru.potemkin.orpheusjetpackcompose.data.model.UploadFileResponse

interface PostApiService {

    @GET("/api/posts/")
    suspend fun getAllPosts(): List<PostDto>

    @GET("/api/posts/creator/{id}")
    suspend fun getCreatorsPosts(@Path("id") id: String): List<PostDto>
    @POST("/api/posts")
    suspend fun createPost(@Body requestBody: RequestBody): PostDto

    @GET("/api/posts/{id}")
    suspend fun getPostById(@Path("id") id: String): PostDto

    @PUT("/api/posts/{id}")
    suspend fun updatePost(
        @Path("id") id: String,
        @Body requestBody: RequestBody
    ): PostDto

    @Multipart
    @POST("/api/upload/minio")
    fun uploadFile(
        @Part file: MultipartBody.Part
    ): UploadFileResponse
}