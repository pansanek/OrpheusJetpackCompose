package ru.potemkin.orpheusjetpackcompose.data.network

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.potemkin.orpheusjetpackcompose.data.model.BandDto

interface BandApiService {

    @GET("/api/bands/")
    suspend fun getAllBands(): List<BandDto>

    @POST("/api/bands/")
    suspend fun createBand(@Body requestBody: RequestBody): BandDto

    @GET("/api/bands/{id}")
    suspend fun getBandById(@Path("id") id: String): BandDto
}