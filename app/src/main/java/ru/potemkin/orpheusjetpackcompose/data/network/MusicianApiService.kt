package ru.potemkin.orpheusjetpackcompose.data.network

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.potemkin.orpheusjetpackcompose.data.model.MusicianDto
import ru.potemkin.orpheusjetpackcompose.data.model.UserDto

interface MusicianApiService {

    @GET("/api/musicians/")
    suspend fun getAllMusicians(): List<MusicianDto>

    @POST("/api/musicians")
    suspend fun createMusician(@Body requestBody: RequestBody): MusicianDto

    @GET("/api/musicians/{id}")
    suspend fun getMusicianById(@Path("id") id: String): MusicianDto

    @PUT("/api/musician/{id}")
    suspend fun updateMusician(
        @Path("id") id: String,
        @Body requestBody: RequestBody
    ): MusicianDto
}