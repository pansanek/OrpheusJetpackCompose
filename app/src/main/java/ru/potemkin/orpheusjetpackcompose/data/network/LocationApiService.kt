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
import ru.potemkin.orpheusjetpackcompose.data.model.LocationDto
import ru.potemkin.orpheusjetpackcompose.data.model.UploadFileResponse
import ru.potemkin.orpheusjetpackcompose.data.model.UserDto

interface LocationApiService {

    @GET("/api/locations/")
    suspend fun getAllLocations(): List<LocationDto>

    @POST("/api/locations/")
    suspend fun createLocation(@Body requestBody: RequestBody): LocationDto

    @GET("/api/locations/{id}")
    suspend fun getLocationById(@Path("id") id: String): LocationDto

    @PUT("/api/locations/{id}")
    suspend fun updateLocation(
        @Path("id") id: String,
        @Body requestBody: RequestBody
    ): LocationDto

    @Multipart
    @POST("/api/upload/minio")
    fun uploadFile(
        @Part file: MultipartBody.Part
    ): UploadFileResponse
}