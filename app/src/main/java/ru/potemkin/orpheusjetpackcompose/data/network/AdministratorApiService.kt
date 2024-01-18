package ru.potemkin.orpheusjetpackcompose.data.network

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.potemkin.orpheusjetpackcompose.data.model.AdministratorDto

interface AdministratorApiService {

    @GET("/api/administrators/")
    suspend fun getAllAdministrators(): List<AdministratorDto>

    @POST("/api/administrators/")
    suspend fun createAdministrator(@Body requestBody: RequestBody): AdministratorDto

    @GET("/api/administrators/{id}")
    suspend fun getAdministratorById(@Path("id") id: String): AdministratorDto
}