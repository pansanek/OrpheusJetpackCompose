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
import ru.potemkin.orpheusjetpackcompose.data.model.BandDto
import ru.potemkin.orpheusjetpackcompose.data.model.LocationDto
import ru.potemkin.orpheusjetpackcompose.data.model.UploadFileResponse

interface BandApiService {

    @GET("/api/bands/")
    suspend fun getAllBands(): List<BandDto>

    @POST("/api/bands/")
    suspend fun createBand(@Body requestBody: RequestBody): BandDto

    @GET("/api/bands/{id}")
    suspend fun getBandById(@Path("id") id: String): BandDto

    @PUT("/api/bands/{id}")
    suspend fun updateBand(
        @Path("id") id: String,
        @Body requestBody: RequestBody
    ): BandDto

    @Multipart
    @POST("/api/upload/minio")
    fun uploadFile(
        @Part file: MultipartBody.Part
    ): UploadFileResponse
}