package ru.potemkin.orpheusjetpackcompose.data.network

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.potemkin.orpheusjetpackcompose.data.model.PostDto

interface PostApiService {

    @GET("/api/posts/")
    suspend fun getAllPosts(): List<PostDto>

    @GET("/api/posts/{id}")
    suspend fun getCreatorsPosts(@Path("id") id: String): List<PostDto>
    @POST("/api/posts")
    suspend fun createPost(@Body requestBody: RequestBody): PostDto

    @GET("/api/posts/{id}")
    suspend fun getPostById(@Path("id") id: String): PostDto
}